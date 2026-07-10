package com.yasinmoridi.traptap.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LevelEntity::class], 
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    // تابعی برای دسترسی به دستورات دیتابیس (DAO)
    abstract fun dao(): AppDao
}
