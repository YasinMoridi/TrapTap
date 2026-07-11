package com.yasinmoridi.traptap.ui.util

data class AppStrings(
    val appName: String,
    val tagline: String,
    val loading: String,
    val puzzleBadge: String,

    // Levels Screen
    val levelsTitle: String,
    val solvedFormat: String,
    val coins: String,
    val packProgress: String,
    val solvedLabel: String,
    val locked: String,
    val completed: String,
    val home: String,
    val rewards: String,
    val profile: String,

    // Game Screen
    val levelPrefix: String,
    val hint: String,
    val restart: String,
    val question: String,
    val options: List<String>,
    val trollMessages: List<String>,
    val simpleHints: Map<Int, String>,
    val detailedHints: Map<Int, String>,
    val correctMsg: String,
    val wrongMsg: String,
    val trollWatching: String,
    val trapLevelLabel: String,
    val victoryTitle: String,
    val victoryMessage: String,
    val victoryReward: String,
    val trollVictoryMessages: List<String>,
    val nextLevel: String,

    // Trap specific strings
    val exitAppToWin: String,
    val exitLabel: String,
    val revealNextLevel: String,
    val answerLabel: String,
    val waitForIt: String,
    val patienceIsKey: String,
    val tooHardForYou: String,
    val giveUp: String,
    val dontLetGo: String,
    val tapMe: String,
    val imTired: String,
    val dizzyMessage: String,
    val louderMessage: String,
    val tooDarkMessage: String,
    val stretchDoorMessage: String,

    // Settings Screen
    val settingsTitle: String,
    val languageLabel: String,
    val themeLabel: String,
    val darkMode: String,
    val lightMode: String,
    val persian: String,
    val english: String,
    val chinese: String,
    val russian: String,
    val german: String,
    val hindi: String,
    val arabic: String,
    val french: String,
    val spanish: String,
    val hebrew: String,
    val turkish: String,
    val vietnamese: String,
    val portuguese: String,
    val japanese: String,
    val korean: String,
    val italian: String,
    val indonesian: String,
    val thai: String,
    val polish: String,
    val dutch: String,
    val ukrainian: String,
    val czech: String,
    val romanian: String,
    val hungarian: String,
    val swedish: String,

    // Hint Dialog
    val hintDialogTitle: String,
    val hintDialogMessage: String,
    val buyHint: String,
    val notEnoughCoins: String,
    val cancel: String,

    // New Hint Labels
    val shop: String,
    val shopTitle: String,
    val shopSubtitle: String,
    val freeCoinsMsg: String,
    val freeCoinsTitle: String,
    val simpleHintLabel: String,
    val detailedHintLabel: String,
    val skipLevelLabel: String,
    val chooseHintTitle: String,
)

// دریافت نام زبان در حالت بومی (Native) برای نمایش در لیست انتخاب زبان
fun getNativeLanguageName(code: String): String {
    return when (code) {
        "fa" -> "🇮🇷 فارسی"
        "zh" -> "🇨🇳 中文"
        "ru" -> "🇷🇺 Русский"
        "de" -> "🇩🇪 Deutsch"
        "hi" -> "🇮🇳 हिन्दी"
        "ar" -> "🇸🇦 العربية"
        "fr" -> "🇫🇷 Français"
        "es" -> "🇪🇸 Español"
        "he" -> "🇮🇱 עברית"
        "tr" -> "🇹🇷 Türkçe"
        "vi" -> "🇻🇳 Tiếng Việt"
        "pt" -> "🇵🇹 Português"
        "ja" -> "🇯🇵 日本語"
        "ko" -> "🇰🇷 한국어"
        "it" -> "🇮🇹 Italiano"
        "id" -> "🇮🇩 Bahasa Indonesia"
        "th" -> "🇹🇭 ไทย"
        "pl" -> "🇵🇱 Polski"
        "nl" -> "🇳🇱 Nederlands"
        "uk" -> "🇺🇦 Українська"
        "cs" -> "🇨🇿 Čeština"
        "ro" -> "🇷🇴 Română"
        "hu" -> "🇭🇺 Magyar"
        "sv" -> "🇸🇪 Svenska"
        else -> "🇺🇸 English"
    }
}

