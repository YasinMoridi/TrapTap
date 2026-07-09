package com.yasinmoridi.traptap.ui

import androidx.lifecycle.ViewModel
import com.yasinmoridi.traptap.ui.util.EnglishStrings
import com.yasinmoridi.traptap.ui.util.PersianStrings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class GameViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(GameState())
    val uiState: StateFlow<GameState> = _uiState.asStateFlow()

    fun toggleLanguage() {
        _uiState.update { currentState ->
            val newStrings = if (currentState.strings == EnglishStrings) PersianStrings else EnglishStrings
            currentState.copy(strings = newStrings)
        }
    }

    fun toggleTheme() {
        _uiState.update { currentState ->
            currentState.copy(isDarkMode = !currentState.isDarkMode)
        }
    }

    fun navigateTo(screen: Screen) {
        _uiState.update { it.copy(currentScreen = screen) }
    }

    fun selectLevel(level: LevelData) {
        if (level.state != LevelState.Locked) {
            _uiState.update { 
                it.copy(
                    currentLevel = level,
                    currentScreen = Screen.Game,
                    selectedOption = null,
                    isAnswered = false,
                    showHint = false
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
    }

    fun toggleHint() {
        _uiState.update { it.copy(showHint = !it.showHint) }
    }

    fun restartLevel() {
        _uiState.update { 
            it.copy(
                selectedOption = null,
                isAnswered = false,
                showHint = false
            )
        }
    }
}
