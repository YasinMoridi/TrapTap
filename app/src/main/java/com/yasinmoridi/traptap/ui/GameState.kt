package com.yasinmoridi.traptap.ui

import com.yasinmoridi.traptap.ui.util.AppStrings
import com.yasinmoridi.traptap.ui.util.EnglishStrings

data class GameState(
    val score: Int = 0,
    val isDarkMode: Boolean = false,
    val strings: AppStrings = EnglishStrings,
    val currentScreen: Screen = Screen.Splash,
    val levels: List<LevelData> = emptyList(),
    val currentLevel: LevelData? = null,
    val selectedOption: String? = null,
    val isAnswered: Boolean = false,
    val showHint: Boolean = false,
    val trollMessageIndex: Int = 0,
    val showSuccessDialog: Boolean = false,
    
    // Level 2 Trap State
    val exitButtonOffset: Pair<Float, Float> = Pair(0f, 0f),
    val level2TrapStarted: Boolean = false
)

enum class Screen {
    Splash, Levels, Game, Settings
}

enum class LevelState {
    Completed, Current, Locked
}

data class LevelData(
    val id: Int,
    val state: LevelState,
    val stars: Int? = null,
    val emoji: String = "🧩"
)
