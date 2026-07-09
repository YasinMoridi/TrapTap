package com.yasinmoridi.traptap.ui

import com.yasinmoridi.traptap.ui.util.AppStrings
import com.yasinmoridi.traptap.ui.util.EnglishStrings

data class GameState(
    val score: Int = 0,
    val isDarkMode: Boolean = false,
    val strings: AppStrings = EnglishStrings,
    val currentScreen: Screen = Screen.Splash,
    val levels: List<LevelData> = initialLevels,
    val currentLevel: LevelData? = null,
    val selectedOption: String? = null,
    val isAnswered: Boolean = false,
    val showHint: Boolean = false,
    val trollMessageIndex: Int = 0
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

val initialLevels = listOf(
    LevelData(1, LevelState.Completed, 3, "😂"),
    LevelData(2, LevelState.Completed, 3, "🤣"),
    LevelData(3, LevelState.Completed, 2, "😅"),
    LevelData(4, LevelState.Completed, 3, "🤪"),
    LevelData(5, LevelState.Completed, 1, "😬"),
    LevelData(6, LevelState.Completed, 2, "🙃"),
    LevelData(7, LevelState.Completed, 3, "😜"),
    LevelData(8, LevelState.Current, emoji = "🤔"),
) + (9..20).map { LevelData(it, LevelState.Locked) }
