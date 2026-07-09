package com.yasinmoridi.traptap.ui

import app.cash.turbine.test
import com.yasinmoridi.traptap.data.db.LevelEntity
import com.yasinmoridi.traptap.data.db.SettingsEntity
import com.yasinmoridi.traptap.data.repository.GameRepository
import com.yasinmoridi.traptap.ui.util.EnglishStrings
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GameViewModelTest {

    private lateinit var viewModel: GameViewModel
    private val repository: GameRepository = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        // Default mocks
        coEvery { repository.getSettings() } returns flowOf(SettingsEntity(isDarkMode = false, language = "en"))
        coEvery { repository.getAllLevels() } returns flowOf(listOf(LevelEntity(1, "Current")))
        
        viewModel = GameViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState initially has correct default values`() = runTest {
        // Test that the initial state matches default expectations
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(EnglishStrings, state.strings)
            assertEquals(false, state.isDarkMode)
            // Skip other initial states if any
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `navigateTo updates currentScreen`() = runTest {
        // Trigger navigation and verify state change
        viewModel.navigateTo(Screen.Settings)
        
        viewModel.uiState.test {
            assertEquals(Screen.Settings, awaitItem().currentScreen)
        }
    }
}
