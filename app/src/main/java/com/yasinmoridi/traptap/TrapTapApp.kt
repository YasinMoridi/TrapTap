package com.yasinmoridi.traptap

import android.app.Application
import com.yasinmoridi.traptap.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TrapTapApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TrapTapApp)
            modules(appModule)
        }
    }
}
