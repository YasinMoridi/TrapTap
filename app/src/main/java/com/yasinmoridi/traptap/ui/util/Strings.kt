package com.yasinmoridi.traptap.ui.util

data class AppStrings(
    val appName: String,
    val tagline: String,
    val loading: String,
    val puzzleBadge: String,
    
    // Levels Screen
    val levelsTitle: String,
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
    val hintText: String,
    val correctMsg: String,
    val wrongMsg: String,
    val trollWatching: String,
    
    // Settings Screen
    val settingsTitle: String,
    val languageLabel: String,
    val themeLabel: String,
    val darkMode: String,
    val lightMode: String,
    val persian: String,
    val english: String
)

val EnglishStrings = AppStrings(
    appName = "TrollMind",
    tagline = "Can you outsmart a troll?",
    loading = "Loading puzzles…",
    puzzleBadge = "PUZZLE",
    levelsTitle = "Choose Level",
    coins = "1,240",
    packProgress = "Pack 1 — Chapter 1",
    solvedLabel = "8 of 20 solved",
    locked = "Locked",
    completed = "Done",
    home = "Home",
    rewards = "Rewards",
    profile = "Profile",
    levelPrefix = "Level",
    hint = "Hint",
    restart = "Restart",
    question = "I have keys but no locks. I have space but no room. You can enter but can't go inside. What am I?",
    options = listOf("A keyboard", "A map", "A dream", "A window"),
    trollMessages = listOf(
        "🤔 Think harder, genius…",
        "😈 Are you sure about that?",
        "🙈 That's adorably wrong.",
        "😂 My grandma solved it faster."
    ),
    hintText = "Think about things that have keys… but aren't a lock.",
    correctMsg = "🎉 You actually got it right! (Was it a guess?)",
    wrongMsg = "😂 Nope! The troll laughs at you.",
    trollWatching = "😈 The troll is watching you think…",
    settingsTitle = "Settings",
    languageLabel = "Language",
    themeLabel = "App Theme",
    darkMode = "Dark Mode",
    lightMode = "Light Mode",
    persian = "Persian",
    english = "English"
)

val PersianStrings = AppStrings(
    appName = "ترول‌مایند",
    tagline = "می‌تونی از خرِ ترول باهوش‌تر باشی؟",
    loading = "درحال بارگذاری…",
    puzzleBadge = "پازل",
    levelsTitle = "انتخاب مرحله",
    coins = "۱٬۲۴۰",
    packProgress = "بسته ۱ — فصل ۱",
    solvedLabel = "۸ از ۲۰ حل شده",
    locked = "قفل",
    completed = "حل شد",
    home = "خانه",
    rewards = "جوایز",
    profile = "پروفایل",
    levelPrefix = "مرحله",
    hint = "راهنمایی",
    restart = "شروع مجدد",
    question = "کلید دارم ولی قفل ندارم. فضا دارم ولی اتاق ندارم. می‌تونی واردم بشی ولی داخلم نیای. من چیم؟",
    options = listOf("کیبورد", "نقشه", "رویا", "پنجره"),
    trollMessages = listOf(
        "🤔 بیشتر فکر کن، نابغه…",
        "😈 مطمئنی؟",
        "🙈 اشتباهت بامزه‌ست.",
        "😂 مادربزرگم زودتر حل کرد."
    ),
    hintText = "به چیزایی فکر کن که کلید دارن ولی قفل نیستن.",
    correctMsg = "🎉 واقعاً درست جواب دادی! (شانسی بود؟)",
    wrongMsg = "😂 نه! ترول به ریشت می‌خنده.",
    trollWatching = "😈 ترول داره نگاهت می‌کنه…",
    settingsTitle = "تنظیمات",
    languageLabel = "زبان",
    themeLabel = "تم برنامه",
    darkMode = "حالت تیره",
    lightMode = "حالت روشن",
    persian = "فارسی",
    english = "انگلیسی"
)
