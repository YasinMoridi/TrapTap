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
import com.yasinmoridi.traptap.ui.util.ArabicStrings
import com.yasinmoridi.traptap.ui.util.HebrewStrings
import com.yasinmoridi.traptap.util.AppConstants
import org.koin.androidx.viewmodel.ext.android.viewModel

 //اکتیویتی اصلی برنامه که مسئول راه‌اندازی سنسورها، مدیریت صدا و رندر کردن رابط کاربری است
class MainActivity : ComponentActivity(), SensorEventListener {
    // تزریق ویومدل با استفاده از Koin (با قابلیت بقا هنگام چرخش صفحه)
    private val viewModel: GameViewModel by viewModel()
    
    // مدیریت سنسورها برای مراحل خاص (مانند شتاب‌سنج)
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    // گوش دادن به تغییرات ولوم گوشی در سطح سیستم
    private val volumeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == AppConstants.VOLUME_CHANGED_ACTION) {
                val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
                val current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                val max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                viewModel.onVolumeChanged(current, max)
            }
        }
    }
    
    // مشاهده تغییرات روشنایی صفحه (برای مراحل ترول)
    private val brightnessObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
        override fun onChange(selfChange: Boolean, uri: Uri?) {
            super.onChange(selfChange, uri)
            val brightness = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, 0)
            viewModel.onBrightnessChanged(brightness)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // فعال‌سازی نمایش لبه ‌تا‌ لبه (حذف حاشیه‌های استاتوس بار)
        enableEdgeToEdge()
        
        // تنظیمات سنسور شتاب‌سنج
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        // ثبت ناظر روشنایی صفحه
        contentResolver.registerContentObserver(
            Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS),
            false,
            brightnessObserver
        )

        setContent {
            // ایجاد کنترلر نویگیشن برای جابجایی بین صفحات
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            val state by viewModel.uiState.collectAsState()
            
            // تنظیم جهت صفحه (RTL برای فارسی، عربی و عبری، LTR برای بقیه)
            val layoutDirection = if (state.strings == PersianStrings || state.strings == ArabicStrings || state.strings == HebrewStrings) {
                LayoutDirection.Rtl
            } else {
                LayoutDirection.Ltr
            }

            CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
                TrapTapTheme(darkTheme = state.isDarkMode) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            // نمایش نوار پایین فقط در صفحات 'مراحل' و 'تنظیمات'
                            val showBottomBar = currentDestination?.hasRoute<AppDestination.Levels>() == true ||
                                    currentDestination?.hasRoute<AppDestination.Settings>() == true

                            if (showBottomBar) {
                                BottomNavigationBar(
                                    strings = state.strings,
                                    isDark = state.isDarkMode,
                                    currentDestination = currentDestination,
                                    onNavigate = { destination ->
                                        // جابجایی بین بخش‌های اصلی اپلیکیشن
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
                            // راه‌اندازی گراف نویگیشن و مدیریت صفحات
                            SetUpNavGraph(navController = navController, viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // ثبت سنسورها هنگام بازگشت به برنامه
        accelerometer?.also { acc ->
            sensorManager.registerListener(this, acc, SensorManager.SENSOR_DELAY_UI)
        }
        registerReceiver(volumeReceiver, IntentFilter(AppConstants.VOLUME_CHANGED_ACTION))
    }

    override fun onPause() {
        super.onPause()
        // غیرفعال کردن سنسورها برای بهینه‌سازی باتری
        sensorManager.unregisterListener(this)
        unregisterReceiver(volumeReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        // حذف ناظر روشنایی برای جلوگیری از نشت حافظه
        contentResolver.unregisterContentObserver(brightnessObserver)
    }

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

    // گوش دادن به دکمه‌های سخت‌افزاری ولوم
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
