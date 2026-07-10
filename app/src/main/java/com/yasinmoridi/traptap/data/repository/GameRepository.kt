package com.yasinmoridi.traptap.data.repository

import com.yasinmoridi.traptap.data.db.AppDao
import com.yasinmoridi.traptap.data.db.LevelEntity
import com.yasinmoridi.traptap.data.local.PreferenceManager
import kotlinx.coroutines.flow.Flow


// این لایه وظیفه دارد داده‌ها را از دیتابیس یا DataStore بگیرد و به بخش‌های دیگر (مثل ویومدل) بدهد
interface GameRepository {
    fun getAllLevels(): Flow<List<LevelEntity>>
    suspend fun insertLevels(levels: List<LevelEntity>)
    suspend fun updateLevel(level: LevelEntity)
    
    // متدهای مربوط به تنظیمات و سکه که از DataStore استفاده می‌کنند
    fun getCoins(): Flow<Int>
    suspend fun saveCoins(amount: Int)
    fun isDarkMode(): Flow<Boolean>
    suspend fun setDarkMode(enabled: Boolean)
    fun getLanguage(): Flow<String>
    suspend fun setLanguage(lang: String)
    fun isLevel2TrapActive(): Flow<Boolean>
    suspend fun setLevel2TrapActive(active: Boolean)
}


// این کلاس با DAO و PreferenceManager در ارتباط است
class GameRepositoryImpl(
    private val dao: AppDao,
    private val prefs: PreferenceManager
) : GameRepository {
    
    override fun getAllLevels(): Flow<List<LevelEntity>> = dao.getAllLevels()
    
    override suspend fun insertLevels(levels: List<LevelEntity>) {
        dao.insertLevels(levels)
    }

    override suspend fun updateLevel(level: LevelEntity) {
        dao.updateLevel(level)
    }

    override fun getCoins(): Flow<Int> = prefs.coins
    override suspend fun saveCoins(amount: Int) = prefs.updateCoins(amount)
    
    override fun isDarkMode(): Flow<Boolean> = prefs.isDarkMode
    override suspend fun setDarkMode(enabled: Boolean) = prefs.setDarkMode(enabled)
    
    override fun getLanguage(): Flow<String> = prefs.language
    override suspend fun setLanguage(lang: String) = prefs.setLanguage(lang)
    
    override fun isLevel2TrapActive(): Flow<Boolean> = prefs.isLevel2TrapActive
    override suspend fun setLevel2TrapActive(active: Boolean) = prefs.setLevel2Trap(active)
}
