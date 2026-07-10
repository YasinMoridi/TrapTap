package com.yasinmoridi.traptap.ui.levels

import com.yasinmoridi.traptap.ui.GameState
import kotlinx.coroutines.CoroutineScope

interface LevelHandler {
    fun onAction(action: LevelAction, state: GameState, scope: CoroutineScope, onUpdate: (GameState) -> Unit)
}

sealed class LevelAction {
    data class OptionSelected(val option: String, val isCorrect: Boolean) : LevelAction()
    data class SliderChanged(val value: Float) : LevelAction()
    data class Dragged(val dx: Float, val dy: Float) : LevelAction()
    object TimerStarted : LevelAction()
    object TiredButtonClick : LevelAction()
    data class HoldProgress(val delta: Float) : LevelAction()
    data class GravityChanged(val x: Float, val y: Float, val z: Float) : LevelAction()
    data class VolumeChanged(val current: Int, val max: Int) : LevelAction()
    data class BrightnessChanged(val value: Int) : LevelAction()
    data class Pinch(val scale: Float) : LevelAction()
    object MoveExitButton : LevelAction()
    object NextLevel : LevelAction()
}
