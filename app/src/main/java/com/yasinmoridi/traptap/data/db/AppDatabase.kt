package com.yasinmoridi.traptap.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LevelEntity::class, SettingsEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): AppDao
}
