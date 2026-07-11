package com.yasinmoridi.traptap.data.repository

import com.yasinmoridi.traptap.data.db.AppDao
import com.yasinmoridi.traptap.data.db.LevelEntity
import com.yasinmoridi.traptap.data.local.PreferenceManager
import com.yasinmoridi.traptap.util.AppConstants
import io.mockk.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GameRepositoryTest {

    private lateinit var repository: GameRepository
    private val dao: AppDao = mockk(relaxed = true)
    private val prefs: PreferenceManager = mockk(relaxed = true)

    @Before
    fun setup() {
        repository = GameRepositoryImpl(dao, prefs)
    }

    @Test
    fun `getAllLevels returns levels from dao`() = runTest {
        val levels = listOf(LevelEntity(1, AppConstants.STATE_CURRENT))
        coEvery { dao.getAllLevels() } returns flowOf(levels)

        val result = repository.getAllLevels().first()

        assertEquals(levels, result)
        coVerify { dao.getAllLevels() }
    }

    @Test
    fun `insertLevels calls dao insertLevels`() = runTest {
        val levels = listOf(LevelEntity(1, AppConstants.STATE_CURRENT))
        repository.insertLevels(levels)
        coVerify { dao.insertLevels(levels) }
    }

    @Test
    fun `updateLevel calls dao updateLevel`() = runTest {
        val level = LevelEntity(1, AppConstants.STATE_COMPLETED)
        repository.updateLevel(level)
        coVerify { dao.updateLevel(level) }
    }

    @Test
    fun `getCoins returns coins from prefs`() = runTest {
        coEvery { prefs.coins } returns flowOf(150)
        val result = repository.getCoins().first()
        assertEquals(150, result)
    }

    @Test
    fun `saveCoins calls prefs updateCoins`() = runTest {
        repository.saveCoins(200)
        coVerify { prefs.updateCoins(200) }
    }

    @Test
    fun `isDarkMode returns value from prefs`() = runTest {
        coEvery { prefs.isDarkMode } returns flowOf(true)
        val result = repository.isDarkMode().first()
        assertEquals(true, result)
    }

    @Test
    fun `setDarkMode calls prefs setDarkMode`() = runTest {
        repository.setDarkMode(false)
        coVerify { prefs.setDarkMode(false) }
    }

    @Test
    fun `getLanguage returns value from prefs`() = runTest {
        coEvery { prefs.language } returns flowOf("fa")
        val result = repository.getLanguage().first()
        assertEquals("fa", result)
    }

    @Test
    fun `setLanguage calls prefs setLanguage`() = runTest {
        repository.setLanguage("en")
        coVerify { prefs.setLanguage("en") }
    }
}
