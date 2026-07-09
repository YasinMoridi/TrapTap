package com.yasinmoridi.traptap.data.repository

import com.yasinmoridi.traptap.data.db.AppDao
import com.yasinmoridi.traptap.data.db.LevelEntity
import com.yasinmoridi.traptap.data.db.SettingsEntity
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun getAllLevels(): Flow<List<LevelEntity>>
    suspend fun insertLevels(levels: List<LevelEntity>)
    suspend fun updateLevel(level: LevelEntity)
    fun getSettings(): Flow<SettingsEntity?>
    suspend fun saveSettings(settings: SettingsEntity)
}

class GameRepositoryImpl(private val dao: AppDao) : GameRepository {
    override fun getAllLevels(): Flow<List<LevelEntity>> = dao.getAllLevels()
    
    override suspend fun insertLevels(levels: List<LevelEntity>) {
        dao.insertLevels(levels)
    }

    override suspend fun updateLevel(level: LevelEntity) {
        dao.updateLevel(level)
    }

    override fun getSettings(): Flow<SettingsEntity?> = dao.getSettings()

    override suspend fun saveSettings(settings: SettingsEntity) {
        dao.saveSettings(settings)
    }
}
