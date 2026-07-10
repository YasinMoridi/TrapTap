package com.yasinmoridi.traptap.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yasinmoridi.traptap.ui.LevelState
import com.yasinmoridi.traptap.util.AppConstants

@Entity(tableName = AppConstants.TABLE_LEVELS)
data class LevelEntity(
    @PrimaryKey val id: Int,       // شماره مرحله
    val state: String,             // وضعیت مرحله: Completed, Current, Locked
    val stars: Int? = null,        // تعداد ستاره‌های کسب شده
    val emoji: String = "🧩"        // آیکون یا ایموجی مخصوص این مرحله
)

@Entity(tableName = AppConstants.TABLE_SETTINGS)
data class SettingsEntity(
    @PrimaryKey val id: Int = 0,         // همیشه 0 است چون فقط یک تنظیمات داریم
    val isDarkMode: Boolean = false,     // تم تاریک فعال است یا نه
    val language: String = AppConstants.LANG_EN, // زبان برنامه
    val level2TrapStarted: Boolean = false // وضعیت خاص برای یکی از مراحل ترول (مرحله ۲)
)
