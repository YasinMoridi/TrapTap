package com.yasinmoridi.traptap.data.repository

import com.yasinmoridi.traptap.data.db.AppDao
import com.yasinmoridi.traptap.data.db.LevelEntity
import com.yasinmoridi.traptap.data.db.SettingsEntity
import kotlinx.coroutines.flow.Flow


// این لایه وظیفه دارد داده‌ها را از دیتابیس بگیرد و به بخش‌های دیگر (مثل ویومدل) بدهد

interface GameRepository {
    fun getAllLevels(): Flow<List<LevelEntity>>
    suspend fun insertLevels(levels: List<LevelEntity>)
    suspend fun updateLevel(level: LevelEntity)
    fun getSettings(): Flow<SettingsEntity?>
    suspend fun saveSettings(settings: SettingsEntity)
}


// این کلاس مستقیماً با DAO در ارتباط است تا عملیات‌های دیتابیس را انجام دهد

class GameRepositoryImpl(private val dao: AppDao) : GameRepository {
    
    // گرفتن تمام مراحل از دیتابیس
    override fun getAllLevels(): Flow<List<LevelEntity>> = dao.getAllLevels()
    
    // وارد کردن مراحل اولیه
    override suspend fun insertLevels(levels: List<LevelEntity>) {
        dao.insertLevels(levels)
    }

    // بروزرسانی یک مرحله (مثلاً بعد از برد)
    override suspend fun updateLevel(level: LevelEntity) {
        dao.updateLevel(level)
    }

    // گرفتن تنظیمات کاربر
    override fun getSettings(): Flow<SettingsEntity?> = dao.getSettings()

    // ذخیره تغییرات در تنظیمات
    override suspend fun saveSettings(settings: SettingsEntity) {
        dao.saveSettings(settings)
    }
}
