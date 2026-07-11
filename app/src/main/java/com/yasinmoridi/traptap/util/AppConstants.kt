package com.yasinmoridi.traptap.util

object AppConstants {
    const val DATABASE_NAME = "traptap.db"
    const val DATASTORE_NAME = "traptap_prefs"
    
    // Tables
    const val TABLE_LEVELS = "levels"

    // Game Config
    const val MAX_LEVEL = 14
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
    const val LANG_ZH = "zh"
    const val LANG_RU = "ru"
    const val LANG_DE = "de"
    const val LANG_HI = "hi"
    const val LANG_AR = "ar"
    const val LANG_FR = "fr"
    const val LANG_ES = "es"
    const val LANG_HE = "he"
    const val LANG_TR = "tr"
    const val LANG_VI = "vi"
    const val LANG_PT = "pt"
    const val LANG_JA = "ja"
    const val LANG_KO = "ko"
    const val LANG_IT = "it"
    const val LANG_ID = "id"
    const val LANG_TH = "th"
    const val LANG_PL = "pl"
    const val LANG_NL = "nl"
    const val LANG_UK = "uk"
    const val LANG_CS = "cs"
    const val LANG_RO = "ro"
    const val LANG_HU = "hu"
    const val LANG_SV = "sv"

    val AVAILABLE_LANGUAGES = listOf(
        LANG_EN, LANG_FA, LANG_ZH, LANG_RU, LANG_DE, LANG_HI,
        LANG_AR, LANG_FR, LANG_ES, LANG_HE, LANG_TR, LANG_VI,
        LANG_PT, LANG_JA, LANG_KO, LANG_IT, LANG_ID, LANG_TH,
        LANG_PL, LANG_NL, LANG_UK, LANG_CS, LANG_RO, LANG_HU,
        LANG_SV
    )

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
