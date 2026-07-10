package com.yasinmoridi.traptap

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.AudioManager
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.yasinmoridi.traptap.ui.GameViewModel
import com.yasinmoridi.traptap.ui.Screen
import com.yasinmoridi.traptap.ui.components.BottomNavigationBar
import com.yasinmoridi.traptap.ui.screens.GameScreen
import com.yasinmoridi.traptap.ui.screens.LevelsScreen
import com.yasinmoridi.traptap.ui.screens.SettingsScreen
import com.yasinmoridi.traptap.ui.screens.SplashScreen
import com.yasinmoridi.traptap.ui.theme.TrapTapTheme
import com.yasinmoridi.traptap.ui.util.PersianStrings
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.koinViewModel

import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.Settings

import android.content.IntentFilter
import android.content.BroadcastReceiver
import android.content.Intent

class MainActivity : ComponentActivity(), SensorEventListener {
    private val viewModel: GameViewModel by inject()
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    private val volumeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "android.media.VOLUME_CHANGED_ACTION") {
                val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
                val current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                val max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                viewModel.onVolumeChanged(current, max)
            }
        }
    }
    
    private val brightnessObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
        override fun onChange(selfChange: Boolean, uri: Uri?) {
            super.onChange(selfChange, uri)
            val brightness = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, 0)
            viewModel.onBrightnessChanged(brightness)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        contentResolver.registerContentObserver(
            Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS),
            false,
            brightnessObserver
        )

        setContent {
            val state by viewModel.uiState.collectAsState()
            
            val layoutDirection = if (state.strings == PersianStrings) {
                LayoutDirection.Rtl
            } else {
                LayoutDirection.Ltr
            }

            CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
                TrapTapTheme(darkTheme = state.isDarkMode) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            if (state.currentScreen == Screen.Levels || state.currentScreen == Screen.Settings) {
                                BottomNavigationBar(
                                    strings = state.strings,
                                    isDark = state.isDarkMode,
                                    currentScreen = state.currentScreen,
                                    onNavigate = { viewModel.navigateTo(it) }
                                )
                            }
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.fillMaxSize()) {
                            when (state.currentScreen) {
                                Screen.Splash -> {
                                    SplashScreen(
                                        strings = state.strings,
                                        isDark = state.isDarkMode,
                                        onFinished = { viewModel.navigateTo(Screen.Levels) },
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                
                                Screen.Levels -> {
                                    LevelsScreen(
                                        strings = state.strings,
                                        levels = state.levels,
                                        isDark = state.isDarkMode,
                                        onLevelClick = { level -> viewModel.selectLevel(level) },
                                        modifier = Modifier.padding(innerPadding)
                                    )
                                }
                                
                                Screen.Settings -> {
                                    SettingsScreen(
                                        strings = state.strings,
                                        isDark = state.isDarkMode,
                                        onToggleTheme = { viewModel.toggleTheme() },
                                        onToggleLanguage = { viewModel.toggleLanguage() },
                                        modifier = Modifier.padding(innerPadding)
                                    )
                                }

                                Screen.Game -> {
                                    GameScreen(
                                        strings = state.strings,
                                        level = state.currentLevel,
                                        isDark = state.isDarkMode,
                                        selectedOption = state.selectedOption,
                                        isAnswered = state.isAnswered,
                                        showHint = state.showHint,
                                        trollMessageIndex = state.trollMessageIndex,
                                        showSuccessDialog = state.showSuccessDialog,
                                        exitButtonOffset = state.exitButtonOffset,
                                        sliderValue = state.sliderValue,
                                        questionOffset = state.questionOffset,
                                        timer = state.timer,
                                        buttonTapCount = state.buttonTapCount,
                                        holdProgress = state.holdProgress,
                                        pinchScale = state.pinchScale,
                                        onBack = { viewModel.navigateTo(Screen.Levels) },
                                        onAction = { viewModel.handleAction(it) },
                                        onToggleHint = { viewModel.toggleHint() },
                                        onRestart = { viewModel.restartLevel() },
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.also { acc ->
            sensorManager.registerListener(this, acc, SensorManager.SENSOR_DELAY_UI)
        }
        registerReceiver(volumeReceiver, IntentFilter("android.media.VOLUME_CHANGED_ACTION"))
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
        unregisterReceiver(volumeReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
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
