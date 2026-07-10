package com.yasinmoridi.traptap.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasinmoridi.traptap.data.db.LevelEntity
import com.yasinmoridi.traptap.data.db.SettingsEntity
import com.yasinmoridi.traptap.data.repository.GameRepository
import com.yasinmoridi.traptap.ui.levels.LevelAction
import com.yasinmoridi.traptap.ui.levels.LevelLogicDispatcher
import com.yasinmoridi.traptap.ui.util.EnglishStrings
import com.yasinmoridi.traptap.ui.util.PersianStrings
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class GameViewModel(
    private val repository: GameRepository,
    private val levelLogicDispatcher: LevelLogicDispatcher
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

    fun handleAction(action: LevelAction) {
        // Special case for repository actions
        when (action) {
            is LevelAction.MoveExitButton -> {
                viewModelScope.launch {
                    val settings = repository.getSettings().first() ?: SettingsEntity()
                    if (!settings.level2TrapStarted) {
                        repository.saveSettings(settings.copy(level2TrapStarted = true))
                    }
                }
            }
            is LevelAction.NextLevel -> {
                onNextLevel()
                return
            }
            else -> {}
        }

        levelLogicDispatcher.dispatch(
            action = action,
            state = _uiState.value,
            scope = viewModelScope,
            onUpdate = { newState ->
                _uiState.value = newState
            }
        )
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
                    exitButtonOffset = Pair(0f, 0f),
                    sliderValue = 0f,
                    questionOffset = Pair(0f, 0f),
                    timer = 10,
                    isTimerRunning = false,
                    buttonTapCount = 0,
                    holdProgress = 0f,
                    pinchScale = 1f
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
                _uiState.update { 
                    it.copy(
                        currentLevel = nextLevel, 
                        showSuccessDialog = false, 
                        isAnswered = false, 
                        selectedOption = null,
                        exitButtonOffset = Pair(0f, 0f),
                        sliderValue = 0f,
                        questionOffset = Pair(0f, 0f),
                        timer = 10,
                        isTimerRunning = false,
                        buttonTapCount = 0,
                        holdProgress = 0f,
                        pinchScale = 1f
                    ) 
                }
            } else {
                navigateTo(Screen.Levels)
            }
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
                showSuccessDialog = false,
                exitButtonOffset = Pair(0f, 0f),
                sliderValue = 0f,
                questionOffset = Pair(0f, 0f),
                timer = 10,
                isTimerRunning = false,
                buttonTapCount = 0,
                holdProgress = 0f,
                pinchScale = 1f
            )
        }
    }

    // Keep these for sensors/volume for now as they are called from MainActivity
    fun onGravityChanged(x: Float, y: Float, z: Float) {
        handleAction(LevelAction.GravityChanged(x, y, z))
    }

    fun onVolumeChanged(current: Int, max: Int) {
        handleAction(LevelAction.VolumeChanged(current, max))
    }

    fun onBrightnessChanged(value: Int) {
        handleAction(LevelAction.BrightnessChanged(value))
    }
}
