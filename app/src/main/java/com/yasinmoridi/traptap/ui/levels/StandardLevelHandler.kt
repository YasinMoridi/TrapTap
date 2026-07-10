package com.yasinmoridi.traptap.ui.levels

import com.yasinmoridi.traptap.ui.GameState
import kotlinx.coroutines.CoroutineScope
import kotlin.random.Random

class StandardLevelHandler : LevelHandler {
    override fun onAction(
        action: LevelAction,
        state: GameState,
        scope: CoroutineScope,
        onUpdate: (GameState) -> Unit
    ) {
        when (action) {
            is LevelAction.OptionSelected -> {
                if (state.isAnswered) return
                
                // Special case for Level 6
                val actualIsCorrect = if (state.currentLevel?.id == 6) {
                    action.option == "GiveUp"
                } else {
                    action.isCorrect
                }

                val newState = state.copy(
                    selectedOption = action.option,
                    isAnswered = true,
                    trollMessageIndex = if (!actualIsCorrect) Random.nextInt(state.strings.trollMessages.size) else state.trollMessageIndex,
                    showSuccessDialog = actualIsCorrect
                )
                onUpdate(newState)
            }
            else -> {}
        }
    }
}
