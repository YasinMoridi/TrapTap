package com.yasinmoridi.traptap.di

import androidx.room.Room
import com.yasinmoridi.traptap.data.db.AppDatabase
import com.yasinmoridi.traptap.data.repository.GameRepository
import com.yasinmoridi.traptap.data.repository.GameRepositoryImpl
import com.yasinmoridi.traptap.ui.GameViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Database
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "traptap.db"
        ).fallbackToDestructiveMigration().build()
    }

    // DAO
    single { get<AppDatabase>().dao() }

    // Repository
    single<GameRepository> { GameRepositoryImpl(get()) }

    // ViewModel
    viewModel { GameViewModel(get()) }
}
