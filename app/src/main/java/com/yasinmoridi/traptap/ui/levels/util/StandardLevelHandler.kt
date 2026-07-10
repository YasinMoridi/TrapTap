package com.yasinmoridi.traptap.ui.levels.util

import com.yasinmoridi.traptap.ui.GameState
import com.yasinmoridi.traptap.util.AppConstants
import kotlinx.coroutines.CoroutineScope
import kotlin.random.Random

/**
 * مدیریت منطق مراحل استاندارد (چهارگزینه‌ای).
 * این کلاس پاسخ‌های درست و غلط را چک کرده و پیام‌های ترول را مدیریت می‌کند.
 */
class StandardLevelHandler : LevelHandler {
    override fun onAction(
        action: LevelAction,
        state: GameState,
        scope: CoroutineScope,
        onUpdate: (GameState) -> Unit
    ) {
        when (action) {
            is LevelAction.OptionSelected -> {
                // اگر قبلاً پاسخ داده شده، کاری انجام نده
                if (state.isAnswered) return
                
                // مورد خاص برای مرحله ۶: در این مرحله گزینه "تسلیم" همان گزینه صحیح است!
                val actualIsCorrect = if (state.currentLevel?.id == 6) {
                    action.option == AppConstants.ACTION_GIVE_UP
                } else {
                    action.isCorrect
                }

                val newState = state.copy(
                    selectedOption = action.option,
                    isAnswered = true,
                    // اگر پاسخ اشتباه بود، یک پیام تمسخرآمیز تصادفی انتخاب کن
                    trollMessageIndex = if (!actualIsCorrect) Random.nextInt(state.strings.trollMessages.size) else state.trollMessageIndex,
                    showSuccessDialog = actualIsCorrect,
                    victoryTrollMessageIndex = if (actualIsCorrect) Random.nextInt(state.strings.trollVictoryMessages.size) else state.victoryTrollMessageIndex
                )
                onUpdate(newState)
            }
            else -> {}
        }
    }
}
