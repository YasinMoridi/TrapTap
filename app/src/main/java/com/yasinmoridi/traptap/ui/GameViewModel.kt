package com.yasinmoridi.traptap.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasinmoridi.traptap.data.db.AppDao
import com.yasinmoridi.traptap.data.db.LevelEntity
import com.yasinmoridi.traptap.data.db.SettingsEntity
import com.yasinmoridi.traptap.ui.util.EnglishStrings
import com.yasinmoridi.traptap.ui.util.PersianStrings
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel(
    private val dao: AppDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(GameState())
    val uiState: StateFlow<GameState> = _uiState.asStateFlow()

    init {
        observeSettings()
        observeLevels()
    }

    private fun observeSettings() {
        dao.getSettings().onEach { entity ->
            entity?.let {
                _uiState.update { state ->
                    state.copy(
                        isDarkMode = it.isDarkMode,
                        strings = if (it.language == "fa") PersianStrings else EnglishStrings
                    )
                }
            } ?: run {
                val systemLang = java.util.Locale.getDefault().language
                if (systemLang == "fa") toggleLanguage()
            }
        }.launchIn(viewModelScope)
    }

    private fun observeLevels() {
        dao.getAllLevels().onEach { entities ->
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
            val initial = listOf(
                LevelEntity(1, "Current", emoji = "🧐"),
            ) + (2..20).map { LevelEntity(it, "Locked") }
            dao.insertLevels(initial)
        }
    }

    fun toggleLanguage() {
        viewModelScope.launch {
            val newLang = if (_uiState.value.strings == EnglishStrings) "fa" else "en"
            dao.saveSettings(SettingsEntity(isDarkMode = _uiState.value.isDarkMode, language = newLang))
        }
    }

    fun toggleTheme() {
        viewModelScope.launch {
            dao.saveSettings(SettingsEntity(isDarkMode = !_uiState.value.isDarkMode, language = if (_uiState.value.strings == PersianStrings) "fa" else "en"))
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
                    showSuccessDialog = false
                )
            }
        }
    }

    fun onOptionSelected(option: String, isCorrect: Boolean) {
        if (_uiState.value.isAnswered) return
        
        _uiState.update { 
            it.copy(
                selectedOption = option,
                isAnswered = true,
                trollMessageIndex = if (!isCorrect) Random.nextInt(it.strings.trollMessages.size) else it.trollMessageIndex
            )
        }
        
        if (isCorrect) {
            _uiState.update { it.copy(showSuccessDialog = true) }
        }
    }

    fun onNextLevel() {
        val currentId = _uiState.value.currentLevel?.id ?: return
        viewModelScope.launch {
            // Mark current as completed
            dao.updateLevel(LevelEntity(currentId, "Completed", 3))
            
            // Unlock next
            val nextId = currentId + 1
            if (nextId <= 20) {
                dao.updateLevel(LevelEntity(nextId, "Current"))
                val nextLevel = _uiState.value.levels.find { it.id == nextId }?.copy(state = LevelState.Current)
                _uiState.update { it.copy(currentLevel = nextLevel, showSuccessDialog = false, isAnswered = false, selectedOption = null) }
            } else {
                navigateTo(Screen.Levels)
            }
        }
    }

    // Level 2 Trap Logic
    fun moveExitButton() {
        _uiState.update { 
            it.copy(
                exitButtonOffset = Pair(
                    Random.nextInt(-200, 200).toFloat(),
                    Random.nextInt(-300, 300).toFloat()
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
