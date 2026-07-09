package com.yasinmoridi.traptap.data.repository

import com.yasinmoridi.traptap.data.db.AppDao
import com.yasinmoridi.traptap.data.db.LevelEntity
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GameRepositoryTest {

    private lateinit var repository: GameRepository
    private val dao: AppDao = mockk(relaxed = true)

    @Before
    fun setup() {
        repository = GameRepositoryImpl(dao)
    }

    @Test
    fun `getAllLevels calls dao getAllLevels`() = runTest {
        // Verify that repository correctly delegates call to DAO
        repository.getAllLevels()
        coVerify { dao.getAllLevels() }
    }

    @Test
    fun `insertLevels calls dao insertLevels`() = runTest {
        // Verify that inserting levels through repository hits the DAO
        val levels = listOf(LevelEntity(1, "Current"))
        repository.insertLevels(levels)
        coVerify { dao.insertLevels(levels) }
    }
}
