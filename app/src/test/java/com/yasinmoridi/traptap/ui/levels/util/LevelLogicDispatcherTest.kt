package com.yasinmoridi.traptap.ui.levels.util

import com.yasinmoridi.traptap.ui.GameState
import com.yasinmoridi.traptap.ui.LevelData
import com.yasinmoridi.traptap.ui.LevelState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LevelLogicDispatcherTest {

    private lateinit var dispatcher: LevelLogicDispatcher
    private val testScope = TestScope()

    @Before
    fun setup() {
        dispatcher = LevelLogicDispatcher()
    }

    @Test
    fun `SliderChanged on Level 3 triggers success when near end`() = runTest {
        val initialState = GameState(
            currentLevel = LevelData(3, LevelState.Current, 0, "")
        )
        
        var updatedState: GameState? = null
        dispatcher.dispatch(
            action = LevelAction.SliderChanged(0.95f),
            state = initialState,
            scope = testScope,
            onUpdate = { updatedState = it }
        )
        
        assertTrue(updatedState?.showSuccessDialog == true)
    }

    @Test
    fun `Dragged on Level 4 triggers success when offset is large`() = runTest {
        val initialState = GameState(
            currentLevel = LevelData(4, LevelState.Current, 0, "")
        )
        
        var updatedState: GameState? = null
        dispatcher.dispatch(
            action = LevelAction.Dragged(500f, 0f),
            state = initialState,
            scope = testScope,
            onUpdate = { updatedState = it }
        )
        
        assertTrue(updatedState?.showSuccessDialog == true)
    }

    @Test
    fun `TiredButtonClick on Level 12 triggers success after 10 clicks`() = runTest {
        val initialState = GameState(
            currentLevel = LevelData(12, LevelState.Current, 0, ""),
            buttonTapCount = 9
        )
        
        var updatedState: GameState? = null
        dispatcher.dispatch(
            action = LevelAction.TiredButtonClick,
            state = initialState,
            scope = testScope,
            onUpdate = { updatedState = it }
        )
        
        assertTrue(updatedState?.showSuccessDialog == true)
        assertTrue(updatedState?.buttonTapCount == 10)
    }

    @Test
    fun `HoldProgress on Level 10 triggers success when progress reaches 1`() = runTest {
        val initialState = GameState(
            currentLevel = LevelData(10, LevelState.Current, 0, ""),
            holdProgress = 0.9f
        )
        
        var updatedState: GameState? = null
        dispatcher.dispatch(
            action = LevelAction.HoldProgress(0.1f),
            state = initialState,
            scope = testScope,
            onUpdate = { updatedState = it }
        )
        
        assertTrue(updatedState?.showSuccessDialog == true)
        assertTrue(updatedState?.holdProgress == 1.0f)
    }
}
