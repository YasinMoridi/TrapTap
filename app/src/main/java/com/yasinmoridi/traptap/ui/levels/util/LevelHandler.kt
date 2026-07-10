package com.yasinmoridi.traptap.ui.levels.util

import com.yasinmoridi.traptap.ui.GameState
import kotlinx.coroutines.CoroutineScope


// رابط مدیریت منطق مراحل
interface LevelHandler {
    /**
     * اجرای یک اکشن خاص در یک مرحله.
     * @param action نوع اکشنی که اتفاق افتاده (مثلاً کلیک یا تغییر اسلایدر)
     * @param state وضعیت فعلی بازی
     * @param scope اسکوپ کوروتین برای انجام کارهای پس‌زمینه (مثل تایمر)
     * @param onUpdate تابعی برای بروزرسانی وضعیت جدید بازی
     */
    fun onAction(action: LevelAction, state: GameState, scope: CoroutineScope, onUpdate: (GameState) -> Unit)
}


// تمام اکشن‌های ممکنی که کاربر می‌تواند در طول بازی انجام دهد

sealed class LevelAction {
    data class OptionSelected(val option: String, val isCorrect: Boolean) : LevelAction() // انتخاب یک گزینه
    data class SliderChanged(val value: Float) : LevelAction() // تغییر مقدار اسلایدر
    data class Dragged(val dx: Float, val dy: Float) : LevelAction() // کشیدن المان‌ها روی صفحه
    object TimerStarted : LevelAction() // شروع تایمر
    object TiredButtonClick : LevelAction() // کلیک روی دکمه خسته (مرحله ۱۲)
    data class HoldProgress(val delta: Float) : LevelAction() // نگه داشتن دکمه (مرحله ۱۰)
    data class GravityChanged(val x: Float, val y: Float, val z: Float) : LevelAction() // تغییر جهت گوشی (سنسور)
    data class VolumeChanged(val current: Int, val max: Int) : LevelAction() // تغییر ولوم صدا
    data class BrightnessChanged(val value: Int) : LevelAction() // تغییر نور صفحه
    data class Pinch(val scale: Float) : LevelAction() // حرکت دو انگشتی (Pinch)
    object MoveExitButton : LevelAction() // جابجایی دکمه خروج (تله مرحله ۲)
    object NextLevel : LevelAction() // رفتن به مرحله بعدی
    object ToggleHintDialog : LevelAction() // نمایش/مخفی کردن دیالوگ خرید راهنمایی
    data class PurchaseHint(val level: Int) : LevelAction() // خرید راهنمایی (۱: ساده، ۲: کامل، ۳: رد کردن)
}
