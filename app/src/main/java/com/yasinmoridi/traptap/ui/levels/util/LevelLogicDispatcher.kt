package com.yasinmoridi.traptap.ui.levels.util

import com.yasinmoridi.traptap.ui.GameState
import kotlinx.coroutines.CoroutineScope
import kotlin.random.Random

// توزیع‌کننده منطق بازی.
class LevelLogicDispatcher {
    private val standardHandler = StandardLevelHandler()
    private val level5Handler = Level5Handler()


     // مدیریت و هدایت اکشن به هندلر مناسب
    fun dispatch(
        action: LevelAction,
        state: GameState,
        scope: CoroutineScope,
        onUpdate: (GameState) -> Unit
    ) {
        val levelId = state.currentLevel?.id ?: 0
        
        // انتخاب هندلر بر اساس آی‌دی مرحله
        when (levelId) {
            5 -> level5Handler.onAction(action, state, scope, onUpdate)
            
            // برای بقیه مراحل از منطق عمومی یا هندلر استاندارد استفاده می‌کنیم
            else -> handleGenericAction(action, state, scope, onUpdate, levelId)
        }
    }


     // مدیریت اکشن‌های عمومی که در اکثر مراحل یا مراحل خاص بدون هندلر جداگانه استفاده می‌شوند

    private fun handleGenericAction(
        action: LevelAction,
        state: GameState,
        scope: CoroutineScope,
        onUpdate: (GameState) -> Unit,
        levelId: Int
    ) {
        when (action) {
            // انتخاب گزینه در مراحل معمولی
            is LevelAction.OptionSelected -> standardHandler.onAction(action, state, scope, onUpdate)
            
            // مرحله ۳: کشیدن اسلایدر تا انتها
            is LevelAction.SliderChanged -> {
                onUpdate(state.copy(sliderValue = action.value))
                if (action.value >= 0.9f && levelId == 3) {
                    onUpdate(state.copy(showSuccessDialog = true))
                }
            }
            
            // مرحله ۴: کشیدن کارت سوال به کنار برای دیدن جواب مخفی
            is LevelAction.Dragged -> {
                val newX = state.questionOffset.first + action.dx
                val newY = state.questionOffset.second + action.dy
                // اگر کارت به اندازه کافی جابجا شده باشد، کاربر برنده می‌شود
                if ((Math.abs(newX) > 400 || Math.abs(newY) > 400) && levelId == 4) {
                    onUpdate(state.copy(questionOffset = Pair(newX, newY), showSuccessDialog = true))
                } else {
                    onUpdate(state.copy(questionOffset = Pair(newX, newY)))
                }
            }

            // مرحله ۱۲: کلیک زیاد روی دکمه تا خسته شود
            is LevelAction.TiredButtonClick -> {
                val currentCount = state.buttonTapCount + 1
                onUpdate(state.copy(buttonTapCount = currentCount))
                if (currentCount >= 10 && levelId == 12) {
                    onUpdate(state.copy(showSuccessDialog = true))
                }
            }

            // مرحله ۱۰: نگه داشتن دکمه به مدت طولانی
            is LevelAction.HoldProgress -> {
                val newProgress = (state.holdProgress + action.delta).coerceIn(0f, 1f)
                onUpdate(state.copy(holdProgress = newProgress))
                if (newProgress >= 1f && levelId == 10) {
                    onUpdate(state.copy(showSuccessDialog = true))
                }
            }

            // مرحله ۷: وارونه کردن گوشی
            is LevelAction.GravityChanged -> {
                if (levelId == 7 && (action.z < -7f || action.y < -7f)) {
                    onUpdate(state.copy(showSuccessDialog = true))
                }
            }

            // مرحله ۸: بالا بردن صدای گوشی تا انتها
            is LevelAction.VolumeChanged -> {
                if (levelId == 8 && action.current >= action.max) {
                    onUpdate(state.copy(showSuccessDialog = true))
                }
            }

            // مرحله ۹: تغییر نور صفحه (خیلی تاریک یا خیلی روشن)
            is LevelAction.BrightnessChanged -> {
                if (levelId == 9 && (action.value > 240 || action.value < 15)) {
                    onUpdate(state.copy(showSuccessDialog = true))
                }
            }

            // مرحله ۱۱: بزرگ کردن در با دو انگشت (Pinch)
            is LevelAction.Pinch -> {
                onUpdate(state.copy(pinchScale = action.scale))
                if (action.scale > 2.5f && levelId == 11) {
                    onUpdate(state.copy(showSuccessDialog = true))
                }
            }

            // تله مرحله ۲: جابجایی تصادفی دکمه خروج
            is LevelAction.MoveExitButton -> {
                onUpdate(state.copy(
                    exitButtonOffset = Pair(
                        Random.nextInt(-250, 250).toFloat(),
                        Random.nextInt(-400, 400).toFloat()
                    )
                ))
            }
            else -> {}
        }
    }
}
