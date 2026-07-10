package com.yasinmoridi.traptap.ui.levels.util

import com.yasinmoridi.traptap.ui.GameState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

// در این مرحله کاربر باید تا پایان تایمر و چند ثانیه بعد از آن صبر کند تا برنده شود
class Level5Handler : LevelHandler {
    override fun onAction(
        action: LevelAction,
        state: GameState,
        scope: CoroutineScope,
        onUpdate: (GameState) -> Unit
    ) {
        when (action) {
            is LevelAction.TimerStarted -> {
                // اگر تایمر قبلاً شروع شده، دوباره شروعش نکن
                if (state.isTimerRunning) return
                
                // مقدار اولیه تایمر را روی ۱۰ ثانیه تنظیم کن
                onUpdate(state.copy(isTimerRunning = true, timer = 10))
                
                scope.launch {
                    var currentTimer = 10
                    while (currentTimer > 0) {
                        delay(1000.milliseconds) // یک ثانیه صبر کن
                        currentTimer -= 1
                        // هر ثانیه UI را با مقدار جدید تایمر بروزرسانی کن
                        onUpdate(state.copy(isTimerRunning = true, timer = currentTimer))
                    }
                    
                    // بعد از اینکه تایمر به صفر رسید، ۳ ثانیه دیگه هم کاربر رو منتظر بذار (تله نهایی!)
                    delay(3000.milliseconds)
                    // حالا دیالوگ پیروزی رو نشون بده
                    onUpdate(state.copy(
                        showSuccessDialog = true,
                        victoryTrollMessageIndex = Random.nextInt(state.strings.trollVictoryMessages.size)
                    ))
                }
            }
            else -> {}
        }
    }
}
