package com.yasinmoridi.traptap.util

object AppConstants {
    const val DATABASE_NAME = "traptap.db"
    const val DATASTORE_NAME = "traptap_prefs"
    
    // Tables
    const val TABLE_LEVELS = "levels"

    // Game Config
    const val MAX_LEVEL = 12
    const val INITIAL_COINS = 100
    const val WIN_REWARD = 50
    
    // Hint Costs
    const val COST_SIMPLE_HINT = 10
    const val COST_DETAILED_HINT = 25
    const val COST_SKIP_LEVEL = 60

    // Level States
    const val STATE_COMPLETED = "Completed"
    const val STATE_CURRENT = "Current"
    const val STATE_LOCKED = "Locked"

    // System Actions
    const val VOLUME_CHANGED_ACTION = "android.media.VOLUME_CHANGED_ACTION"

    // Language
    const val LANG_EN = "en"
    const val LANG_FA = "fa"

    // Game Actions
    const val ACTION_GIVE_UP = "GiveUp"

    // Icons & Emojis
    const val ICON_HOME = "🏠"
    const val ICON_HINT = "💡"
    const val ICON_RESTART = "↺"
    const val ICON_BACK = "←"
    const val ICON_VICTORY = "🎉"
    const val ICON_DEFAULT_PUZZLE = "🧩"
    const val ICON_TROLL = "😈"
    const val ICON_CORRECT = "✓"
    const val ICON_WRONG = "✗"
    const val ICON_COIN = "🪙"
    const val ICON_LOCK = "🔒"
    const val ICON_STAR = "★"

    // Options
    val OPTION_KEYS = listOf("A", "B", "C", "D")
    const val CORRECT_OPTION_INDEX = 0 // Assume first option is always correct for simplicity in standard levels
}
