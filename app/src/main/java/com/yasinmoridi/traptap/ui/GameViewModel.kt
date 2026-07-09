package com.yasinmoridi.traptap.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasinmoridi.traptap.data.db.LevelEntity
import com.yasinmoridi.traptap.data.db.SettingsEntity
import com.yasinmoridi.traptap.data.repository.GameRepository
import com.yasinmoridi.traptap.ui.util.EnglishStrings
import com.yasinmoridi.traptap.ui.util.PersianStrings
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel(
    private val repository: GameRepository
) : ViewModel() {

    companion object {
        const val MAX_LEVEL = 12
    }

    private val _uiState = MutableStateFlow(GameState())
    val uiState: StateFlow<GameState> = _uiState.asStateFlow()

    init {
        observeSettings()
        observeLevels()
    }

    private fun observeSettings() {
        repository.getSettings().onEach { entity ->
            entity?.let {
                _uiState.update { state ->
                    state.copy(
                        isDarkMode = it.isDarkMode,
                        strings = if (it.language == "fa") PersianStrings else EnglishStrings
                    )
                }
                
                if (it.level2TrapStarted) {
                    checkLevel2Win()
                }
            } ?: run {
                val systemLang = java.util.Locale.getDefault().language
                if (systemLang == "fa") toggleLanguage()
            }
        }.launchIn(viewModelScope)
    }

    private fun checkLevel2Win() {
        viewModelScope.launch {
            val levels = repository.getAllLevels().first()
            val level2 = levels.find { it.id == 2 }
            if (level2?.state == "Current") {
                repository.updateLevel(LevelEntity(2, "Completed", 3))
                repository.updateLevel(LevelEntity(3, "Current"))
                
                val currentSettings = repository.getSettings().first()
                currentSettings?.let {
                    repository.saveSettings(it.copy(level2TrapStarted = false))
                }
            }
        }
    }

    private fun observeLevels() {
        repository.getAllLevels().onEach { entities ->
            if (entities.isEmpty()) {
                initLevels()
            } else {
                val levels = entities.map {
                    LevelData(it.id, LevelState.valueOf(it.state), it.stars, it.emoji)
                }
                _uiState.update { it.copy(levels = levels) }
            }
        }.launchIn(viewModelScope)
    }

    private fun initLevels() {
        viewModelScope.launch {
            val initial = (1..MAX_LEVEL).map { 
                LevelEntity(it, if (it == 1) "Current" else "Locked", emoji = when(it) {
                    2 -> "🚪"
                    3 -> "🎚️"
                    4 -> "🧩"
                    5 -> "⏳"
                    6 -> "🏳️"
                    7 -> "🙃"
                    8 -> "🔊"
                    9 -> "☀️"
                    10 -> "🖐️"
                    11 -> "🤌"
                    12 -> "😫"
                    else -> "🧩"
                })
            }
            repository.insertLevels(initial)
        }
    }

    fun toggleLanguage() {
        viewModelScope.launch {
            val current = repository.getSettings().first() ?: SettingsEntity()
            val newLang = if (_uiState.value.strings == EnglishStrings) "fa" else "en"
            repository.saveSettings(current.copy(language = newLang))
        }
    }

    fun toggleTheme() {
        viewModelScope.launch {
            val current = repository.getSettings().first() ?: SettingsEntity()
            repository.saveSettings(current.copy(isDarkMode = !current.isDarkMode))
        }
    }

    fun navigateTo(screen: Screen) {
        _uiState.update { it.copy(currentScreen = screen, showSuccessDialog = false) }
    }

    fun selectLevel(level: LevelData) {
        if (level.state != LevelState.Locked) {
            _uiState.update { 
                it.copy(
                    currentLevel = level,
                    currentScreen = Screen.Game,
                    selectedOption = null,
                    isAnswered = false,
                    showHint = false,
                    showSuccessDialog = false,
                    exitButtonOffset = Pair(0f, 0f)
                )
            }
            
            // Level 2 Trap: Mark as started as soon as the level is selected
            if (level.id == 2) {
                viewModelScope.launch {
                    val settings = repository.getSettings().first() ?: SettingsEntity()
                    repository.saveSettings(settings.copy(level2TrapStarted = true))
                }
            }
        }
    }

    fun onOptionSelected(option: String, isCorrect: Boolean) {
        if (_uiState.value.isAnswered) return
        
        // Special case for Level 6: "Give Up" is actually correct
        val actualIsCorrect = if (_uiState.value.currentLevel?.id == 6) {
            option == "GiveUp"
        } else {
            isCorrect
        }

        _uiState.update { 
            it.copy(
                selectedOption = option,
                isAnswered = true,
                trollMessageIndex = if (!actualIsCorrect) Random.nextInt(it.strings.trollMessages.size) else it.trollMessageIndex
            )
        }
        
        if (actualIsCorrect) {
            _uiState.update { it.copy(showSuccessDialog = true) }
        }
    }

    // New Trap Actions
    fun updateSlider(value: Float) {
        _uiState.update { it.copy(sliderValue = value) }
        if (value >= 0.9f && _uiState.value.currentLevel?.id == 3) {
            _uiState.update { it.copy(showSuccessDialog = true) }
        }
    }

    fun updateQuestionOffset(dx: Float, dy: Float) {
        _uiState.update { state ->
            val newX = state.questionOffset.first + dx
            val newY = state.questionOffset.second + dy
            
            // Win condition for Level 4: Move it far enough to reveal the secret
            if ((Math.abs(newX) > 400 || Math.abs(newY) > 400) && state.currentLevel?.id == 4) {
                state.copy(questionOffset = Pair(newX, newY), showSuccessDialog = true)
            } else {
                state.copy(questionOffset = Pair(newX, newY))
            }
        }
    }

    fun startLevel5Timer() {
        if (_uiState.value.isTimerRunning) return
        _uiState.update { it.copy(isTimerRunning = true, timer = 10) }
        viewModelScope.launch {
            while (_uiState.value.timer > 0) {
                kotlinx.coroutines.delay(1000)
                _uiState.update { it.copy(timer = it.timer - 1) }
            }
            // After timer hits 0, wait 3 more seconds
            kotlinx.coroutines.delay(3000)
            if (_uiState.value.currentLevel?.id == 5) {
                _uiState.update { it.copy(showSuccessDialog = true) }
            }
        }
    }

    fun onTiredButtonClick() {
        val currentCount = _uiState.value.buttonTapCount + 1
        _uiState.update { it.copy(buttonTapCount = currentCount) }
        if (currentCount >= 10 && _uiState.value.currentLevel?.id == 12) {
            _uiState.update { it.copy(showSuccessDialog = true) }
        }
    }

    fun updateHoldProgress(delta: Float) {
        val newProgress = (_uiState.value.holdProgress + delta).coerceIn(0f, 1f)
        _uiState.update { it.copy(holdProgress = newProgress) }
        if (newProgress >= 1f && _uiState.value.currentLevel?.id == 10) {
            _uiState.update { it.copy(showSuccessDialog = true) }
        }
    }

    fun onGravityChanged(x: Float, y: Float, z: Float) {
        // Level 7: Flipped upside down (Z negative) or turned 180 deg (Y negative)
        // Lowering threshold to -7f for better sensitivity
        if (_uiState.value.currentLevel?.id == 7) {
            if (z < -7f || y < -7f) {
                _uiState.update { it.copy(showSuccessDialog = true) }
            }
        }
    }

    fun onVolumeChanged(current: Int, max: Int) {
        // Checking if volume is at least 90% of max to be less strict
        if (current >= max && _uiState.value.currentLevel?.id == 8) {
            _uiState.update { it.copy(showSuccessDialog = true) }
        }
    }

    fun onBrightnessChanged(value: Int) {
        // value is typically 0-255. If it's near max or min
        if ((value > 240 || value < 15) && _uiState.value.currentLevel?.id == 9) {
            _uiState.update { it.copy(showSuccessDialog = true) }
        }
    }

    fun onPinch(scale: Float) {
        _uiState.update { it.copy(pinchScale = scale) }
        if (scale > 2.5f && _uiState.value.currentLevel?.id == 11) {
            _uiState.update { it.copy(showSuccessDialog = true) }
        }
    }

    fun winLevel() {
        _uiState.update { it.copy(showSuccessDialog = true) }
    }

    fun onNextLevel() {
        val currentId = _uiState.value.currentLevel?.id ?: return
        viewModelScope.launch {
            // Mark current as completed
            repository.updateLevel(LevelEntity(currentId, "Completed", 3))
            
            // Unlock next
            val nextId = currentId + 1
            if (nextId <= MAX_LEVEL) {
                repository.updateLevel(LevelEntity(nextId, "Current"))
                val nextLevel = _uiState.value.levels.find { it.id == nextId }?.copy(state = LevelState.Current)
                _uiState.update { it.copy(currentLevel = nextLevel, showSuccessDialog = false, isAnswered = false, selectedOption = null) }
            } else {
                navigateTo(Screen.Levels)
            }
        }
    }

    // Level 2 Trap Logic
    fun moveExitButton() {
        viewModelScope.launch {
            val settings = repository.getSettings().first() ?: SettingsEntity()
            if (!settings.level2TrapStarted) {
                repository.saveSettings(settings.copy(level2TrapStarted = true))
            }
        }

        _uiState.update { 
            it.copy(
                exitButtonOffset = Pair(
                    Random.nextInt(-250, 250).toFloat(),
                    Random.nextInt(-400, 400).toFloat()
                )
            )
        }
    }

    fun toggleHint() {
        _uiState.update { it.copy(showHint = !it.showHint) }
    }

    fun restartLevel() {
        _uiState.update { 
            it.copy(
                selectedOption = null,
                isAnswered = false,
                showHint = false,
                showSuccessDialog = false
            )
        }
    }
}
