package com.yasinmoridi.traptap.data.db

import androidx.room.*
import com.yasinmoridi.traptap.util.AppConstants
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // گرفتن لیست تمام مراحل به صورت جریانی (Flow) تا هر تغییری سریعاً در اپ دیده شود
    @Query("SELECT * FROM ${AppConstants.TABLE_LEVELS} ORDER BY id ASC")
    fun getAllLevels(): Flow<List<LevelEntity>>

    // ذخیره دسته‌جمعی مراحل (مثلاً در اولین اجرای برنامه)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLevels(levels: List<LevelEntity>)

    // آپدیت کردن وضعیت یک مرحله خاص (مثلاً وقتی کاربر برنده می‌شود)
    @Update
    suspend fun updateLevel(level: LevelEntity)

    // گرفتن تنظیمات برنامه (فقط یک ردیف داریم که آی‌دی آن همیشه 0 است)
    @Query("SELECT * FROM ${AppConstants.TABLE_SETTINGS} WHERE id = 0")
    fun getSettings(): Flow<SettingsEntity?>

    // ذخیره یا بروزرسانی تنظیمات کاربر
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSettings(settings: SettingsEntity)
}
