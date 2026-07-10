package com.yasinmoridi.traptap.ui

import com.yasinmoridi.traptap.ui.util.AppStrings
import com.yasinmoridi.traptap.ui.util.EnglishStrings

data class GameState(
    val score: Int = 0,
    val isDarkMode: Boolean = false,     // وضعیت تم تاریک
    val strings: AppStrings = EnglishStrings, // استرینگ‌های ترجمه شده (فارسی/انگلیسی)
    val currentScreen: Screen = Screen.Splash, // صفحه‌ای که کاربر فعلاً در آن است
    val levels: List<LevelData> = emptyList(), // لیست تمام مراحل دریافت شده از دیتابیس
    val currentLevel: LevelData? = null,       // مرحله‌ای که کاربر در حال بازی کردن آن است
    val selectedOption: String? = null,        // گزینه‌ای که کاربر انتخاب کرده
    val isAnswered: Boolean = false,           // آیا به سوال پاسخ داده شده؟
    val showHint: Boolean = false,             // نمایش راهنمایی
    val trollMessageIndex: Int = 0,            // ایندکس پیام‌های مسخره‌آمیز ترول
    val showSuccessDialog: Boolean = false,    // نمایش دیالوگ پیروزی
    
    // وضعیت‌های مربوط به مراحل خاص (Traps)
    val exitButtonOffset: Pair<Float, Float> = Pair(0f, 0f), // جابجایی دکمه خروج
    val sliderValue: Float = 0f,                             // مقدار اسلایدر در مراحل مربوطه
    val questionOffset: Pair<Float, Float> = Pair(0f, 0f),   // جابجایی متن سوال
    val timer: Int = 10,                                     // تایمر معکوس
    val isTimerRunning: Boolean = false,                     // وضعیت در حال اجرا بودن تایمر
    val buttonTapCount: Int = 0,                             // تعداد کلیک‌ها روی یک دکمه خاص
    val holdProgress: Float = 0f,                            // میزان نگه داشتن دکمه (Progress)
    val isBrightnessActionDone: Boolean = false,             // انجام شدن اکشن مربوط به نور صفحه
    val isVolumeActionDone: Boolean = false,                 // انجام شدن اکشن مربوط به صدا
    val pinchScale: Float = 1f                               // مقدار بزرگنمایی در حرکات دو انگشتی
)


//صفحات مختلف اپلیکیشن
enum class Screen {
    Splash, Levels, Game, Settings
}


// وضعیت‌های ممکن برای هر مرحله
enum class LevelState {
    Completed, // تمام شده
    Current,   // مرحله فعلی (باز)
    Locked     // قفل شده
}


// مدل داده‌ای برای نمایش هر مرحله در لیست
data class LevelData(
    val id: Int,
    val state: LevelState,
    val stars: Int? = null,
    val emoji: String = "🧩"
)
