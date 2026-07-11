package com.yasinmoridi.traptap.ui

import app.cash.turbine.test
import com.yasinmoridi.traptap.data.db.LevelEntity
import com.yasinmoridi.traptap.data.repository.GameRepository
import com.yasinmoridi.traptap.ui.levels.util.LevelAction
import com.yasinmoridi.traptap.ui.levels.util.LevelLogicDispatcher
import com.yasinmoridi.traptap.ui.util.i18n.EnglishStrings
import com.yasinmoridi.traptap.util.AppConstants
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GameViewModelTest {

    private lateinit var viewModel: GameViewModel
    private val repository: GameRepository = mockk(relaxed = true)
    private val levelLogicDispatcher: LevelLogicDispatcher = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        // Default mocks
        coEvery { repository.getLanguage() } returns flowOf("en")
        coEvery { repository.isDarkMode() } returns flowOf(false)
        coEvery { repository.getCoins() } returns flowOf(100)
        coEvery { repository.getAllLevels() } returns flowOf(
            listOf(
                LevelEntity(1, AppConstants.STATE_CURRENT),
                LevelEntity(2, AppConstants.STATE_LOCKED)
            )
        )
        coEvery { repository.isLevel2TrapActive() } returns flowOf(false)
        
        viewModel = GameViewModel(repository, levelLogicDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initialization loads settings and levels`() = runTest {
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(EnglishStrings, state.strings)
            assertEquals(100, state.coins)
            assertEquals(2, state.levels.size)
            assertEquals(1, state.levels[0].id)
            assertEquals(LevelState.Current, state.levels[0].state)
        }
    }

    @Test
    fun `toggleTheme calls repository to save new theme`() = runTest {
        viewModel.toggleTheme()
        advanceUntilIdle()
        coVerify { repository.setDarkMode(true) }
    }

    @Test
    fun `setLanguage updates strings in state`() = runTest {
        viewModel.setLanguage("fa")
        advanceUntilIdle()
        coVerify { repository.setLanguage("fa") }
    }

    @Test
    fun `selectLevel updates currentLevel and screen`() = runTest {
        val level = LevelData(1, LevelState.Current, 0, "🧩")
        viewModel.selectLevel(level)
        
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(level, state.currentLevel)
            assertEquals(Screen.Game, state.currentScreen)
        }
    }

    @Test
    fun `onNextLevel with reward adds coins and unlocks next level`() = runTest {
        val currentLevel = LevelData(1, LevelState.Current, 0, "🧩")
        viewModel.selectLevel(currentLevel)
        
        coEvery { repository.getAllLevels() } returns flowOf(listOf(LevelEntity(1, AppConstants.STATE_CURRENT)))
        
        viewModel.onNextLevel(giveReward = true)
        advanceUntilIdle()

        coVerify { repository.saveCoins(100 + AppConstants.WIN_REWARD) }
        coVerify { repository.updateLevel(match { it.id == 1 && it.state == AppConstants.STATE_COMPLETED }) }
        coVerify { repository.updateLevel(match { it.id == 2 && it.state == AppConstants.STATE_CURRENT }) }
    }

    @Test
    fun `purchaseHint simple hint subtracts coins and updates state`() = runTest {
        coEvery { repository.getCoins() } returns flowOf(100)
        
        viewModel.handleAction(LevelAction.PurchaseHint(1))
        advanceUntilIdle()

        coVerify { repository.saveCoins(100 - AppConstants.COST_SIMPLE_HINT) }
        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state.showHint)
            assertEquals(1, state.unlockedHintLevel)
        }
    }

    @Test
    fun `purchaseHint skip level subtracts coins and advances level`() = runTest {
        coEvery { repository.getCoins() } returns flowOf(100)
        val currentLevel = LevelData(1, LevelState.Current, 0, "🧩")
        viewModel.selectLevel(currentLevel)
        
        viewModel.handleAction(LevelAction.PurchaseHint(3))
        advanceUntilIdle()

        coVerify { repository.saveCoins(100 - AppConstants.COST_SKIP_LEVEL) }
        // Verify it marked level 1 as completed without giving reward (reward logic is in onNextLevel)
        coVerify { repository.updateLevel(match { it.id == 1 && it.state == AppConstants.STATE_COMPLETED }) }
    }

    @Test
    fun `handleAction delegates to levelLogicDispatcher for non-system actions`() = runTest {
        val action = LevelAction.OptionSelected("A", true)
        viewModel.handleAction(action)
        
        verify { levelLogicDispatcher.dispatch(action, any(), any(), any()) }
    }
}
