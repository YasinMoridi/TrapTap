package com.yasinmoridi.traptap.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yasinmoridi.traptap.ui.LevelState

@Entity(tableName = "levels")
data class LevelEntity(
    @PrimaryKey val id: Int,
    val state: String, // "Completed", "Current", "Locked"
    val stars: Int? = null,
    val emoji: String = "🧩"
)

@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey val id: Int = 0,
    val isDarkMode: Boolean = false,
    val language: String = "en",
    val level2TrapStarted: Boolean = false
)
