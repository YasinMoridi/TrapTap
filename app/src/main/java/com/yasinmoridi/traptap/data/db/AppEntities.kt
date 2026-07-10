package com.yasinmoridi.traptap.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yasinmoridi.traptap.util.AppConstants

@Entity(tableName = AppConstants.TABLE_LEVELS)
data class LevelEntity(
    @PrimaryKey val id: Int,       // شماره مرحله
    val state: String,             // وضعیت مرحله: Completed, Current, Locked
    val stars: Int? = null,        // تعداد ستاره‌های کسب شده
    val emoji: String = "🧩"        // آیکون یا ایموجی مخصوص این مرحله
)
