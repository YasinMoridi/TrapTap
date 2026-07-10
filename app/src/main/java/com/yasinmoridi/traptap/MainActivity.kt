package com.yasinmoridi.traptap

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.ContentObserver
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.AudioManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.yasinmoridi.traptap.ui.GameViewModel
import com.yasinmoridi.traptap.ui.navigation.AppDestination
import com.yasinmoridi.traptap.ui.navigation.SetUpNavGraph
import com.yasinmoridi.traptap.ui.components.BottomNavigationBar
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavDestination.Companion.hasRoute
import com.yasinmoridi.traptap.ui.theme.TrapTapTheme
import com.yasinmoridi.traptap.ui.util.PersianStrings
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity(), SensorEventListener {
    // تزریق وابستگی ViewModel با استفاده از Koin
    private val viewModel: GameViewModel by inject()
    
    // مدیریت سنسورها (برای مرحله‌هایی که به شتاب‌سنج نیاز دارند)
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    // گیرنده‌ای برای گوش دادن به تغییرات دکمه‌های ولوم گوشی
    private val volumeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "android.media.VOLUME_CHANGED_ACTION") {
                val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
                val current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                val max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                // خبر دادن به ViewModel که ولوم تغییر کرده
                viewModel.onVolumeChanged(current, max)
            }
        }
    }
    
    // مشاهده‌گر برای تغییرات میزان روشنایی صفحه (برای مراحل خاص)
    private val brightnessObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
        override fun onChange(selfChange: Boolean, uri: Uri?) {
            super.onChange(selfChange, uri)
            val brightness = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, 0)
            viewModel.onBrightnessChanged(brightness)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // فعال‌سازی حالت لبه‌تا‌لبه (تمام صفحه)
        enableEdgeToEdge()
        
        // راه‌اندازی سنسور شتاب‌سنج
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        // ثبت مشاهده‌گر روشنایی صفحه در سیستم
        contentResolver.registerContentObserver(
            Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS),
            false,
            brightnessObserver
        )

        // شروع رندر کردن رابط کاربری با Jetpack Compose
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            // دریافت آخرین وضعیت بازی از ViewModel
            val state by viewModel.uiState.collectAsState()
            
            // تعیین راست‌چین یا چپ‌چین بودن بر اساس زبان انتخابی
            val layoutDirection = if (state.strings == PersianStrings) {
                LayoutDirection.Rtl
            } else {
                LayoutDirection.Ltr
            }

            CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
                // اعمال تم اختصاصی برنامه (تاریک یا روشن)
                TrapTapTheme(darkTheme = state.isDarkMode) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            // نمایش نوار پایین فقط در صفحات لیست مراحل و تنظیمات
                            val showBottomBar = currentDestination?.hasRoute<AppDestination.Levels>() == true ||
                                    currentDestination?.hasRoute<AppDestination.Settings>() == true

                            if (showBottomBar) {
                                BottomNavigationBar(
                                    strings = state.strings,
                                    isDark = state.isDarkMode,
                                    currentDestination = currentDestination,
                                    onNavigate = { destination ->
                                        navController.navigate(destination) {
                                            popUpTo(AppDestination.Levels) { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                            SetUpNavGraph(navController = navController, viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // فعال کردن سنسور و گیرنده ولوم وقتی کاربر وارد برنامه می‌شود
        accelerometer?.also { acc ->
            sensorManager.registerListener(this, acc, SensorManager.SENSOR_DELAY_UI)
        }
        registerReceiver(volumeReceiver, IntentFilter("android.media.VOLUME_CHANGED_ACTION"))
    }

    override fun onPause() {
        super.onPause()
        // غیرفعال کردن سنسورها برای بهینه‌سازی مصرف باتری وقتی کاربر از برنامه خارج می‌شود
        sensorManager.unregisterListener(this)
        unregisterReceiver(volumeReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        // آزاد کردن مشاهده‌گر سیستم برای جلوگیری از نشت حافظه
        contentResolver.unregisterContentObserver(brightnessObserver)
    }

    // این متد هر بار که گوشی تکان بخورد صدا زده می‌شود
    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            viewModel.onGravityChanged(
                event.values[0],
                event.values[1],
                event.values[2]
            )
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    // گوش دادن به دکمه‌های سخت‌افزاری (برای مراحل ولوم صدا)
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
            val max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
            viewModel.onVolumeChanged(current, max)
        }
        return super.onKeyDown(keyCode, event)
    }
}
