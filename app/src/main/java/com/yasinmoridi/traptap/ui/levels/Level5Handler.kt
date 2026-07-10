package com.yasinmoridi.traptap.ui.levels

import com.yasinmoridi.traptap.ui.GameState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Level5Handler : LevelHandler {
    override fun onAction(
        action: LevelAction,
        state: GameState,
        scope: CoroutineScope,
        onUpdate: (GameState) -> Unit
    ) {
        when (action) {
            is LevelAction.TimerStarted -> {
                if (state.isTimerRunning) return
                onUpdate(state.copy(isTimerRunning = true, timer = 10))
                
                scope.launch {
                    var currentTimer = 10
                    while (currentTimer > 0) {
                        delay(1000)
                        currentTimer -= 1
                        onUpdate(state.copy(isTimerRunning = true, timer = currentTimer))
                    }
                    // After timer hits 0, wait 3 more seconds
                    delay(3000)
                    onUpdate(state.copy(showSuccessDialog = true))
                }
            }
            else -> {}
        }
    }
}
