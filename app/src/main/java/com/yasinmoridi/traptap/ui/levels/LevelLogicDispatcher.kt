package com.yasinmoridi.traptap.ui.levels

import com.yasinmoridi.traptap.ui.GameState
import kotlinx.coroutines.CoroutineScope
import kotlin.random.Random

class LevelLogicDispatcher {
    private val standardHandler = StandardLevelHandler()
    private val level5Handler = Level5Handler()

    fun dispatch(
        action: LevelAction,
        state: GameState,
        scope: CoroutineScope,
        onUpdate: (GameState) -> Unit
    ) {
        val levelId = state.currentLevel?.id ?: 0
        
        // Decide which handler to use based on Level ID
        when (levelId) {
            5 -> level5Handler.onAction(action, state, scope, onUpdate)
            
            // For other levels, we can either have specific handlers or use standard logic
            else -> handleGenericAction(action, state, scope, onUpdate, levelId)
        }
    }

    private fun handleGenericAction(
        action: LevelAction,
        state: GameState,
        scope: CoroutineScope,
        onUpdate: (GameState) -> Unit,
        levelId: Int
    ) {
        when (action) {
            is LevelAction.OptionSelected -> standardHandler.onAction(action, state, scope, onUpdate)
            
            is LevelAction.SliderChanged -> {
                onUpdate(state.copy(sliderValue = action.value))
                if (action.value >= 0.9f && levelId == 3) {
                    onUpdate(state.copy(showSuccessDialog = true))
                }
            }
            
            is LevelAction.Dragged -> {
                val newX = state.questionOffset.first + action.dx
                val newY = state.questionOffset.second + action.dy
                if ((Math.abs(newX) > 400 || Math.abs(newY) > 400) && levelId == 4) {
                    onUpdate(state.copy(questionOffset = Pair(newX, newY), showSuccessDialog = true))
                } else {
                    onUpdate(state.copy(questionOffset = Pair(newX, newY)))
                }
            }

            is LevelAction.TiredButtonClick -> {
                val currentCount = state.buttonTapCount + 1
                onUpdate(state.copy(buttonTapCount = currentCount))
                if (currentCount >= 10 && levelId == 12) {
                    onUpdate(state.copy(showSuccessDialog = true))
                }
            }

            is LevelAction.HoldProgress -> {
                val newProgress = (state.holdProgress + action.delta).coerceIn(0f, 1f)
                onUpdate(state.copy(holdProgress = newProgress))
                if (newProgress >= 1f && levelId == 10) {
                    onUpdate(state.copy(showSuccessDialog = true))
                }
            }

            is LevelAction.GravityChanged -> {
                if (levelId == 7 && (action.z < -7f || action.y < -7f)) {
                    onUpdate(state.copy(showSuccessDialog = true))
                }
            }

            is LevelAction.VolumeChanged -> {
                if (levelId == 8 && action.current >= action.max) {
                    onUpdate(state.copy(showSuccessDialog = true))
                }
            }

            is LevelAction.BrightnessChanged -> {
                if (levelId == 9 && (action.value > 240 || action.value < 15)) {
                    onUpdate(state.copy(showSuccessDialog = true))
                }
            }

            is LevelAction.Pinch -> {
                onUpdate(state.copy(pinchScale = action.scale))
                if (action.scale > 2.5f && levelId == 11) {
                    onUpdate(state.copy(showSuccessDialog = true))
                }
            }

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
