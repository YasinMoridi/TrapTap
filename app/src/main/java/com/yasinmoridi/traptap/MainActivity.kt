package com.yasinmoridi.traptap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yasinmoridi.traptap.ui.GameViewModel
import com.yasinmoridi.traptap.ui.Screen
import com.yasinmoridi.traptap.ui.components.BottomNavigationBar
import com.yasinmoridi.traptap.ui.screens.GameScreen
import com.yasinmoridi.traptap.ui.screens.LevelsScreen
import com.yasinmoridi.traptap.ui.screens.SettingsScreen
import com.yasinmoridi.traptap.ui.screens.SplashScreen
import com.yasinmoridi.traptap.ui.theme.TrapTapTheme
import com.yasinmoridi.traptap.ui.util.PersianStrings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: GameViewModel = viewModel()
            val state by viewModel.uiState.collectAsState()
            
            val layoutDirection = if (state.strings == PersianStrings) {
                LayoutDirection.Rtl
            } else {
                LayoutDirection.Ltr
            }

            CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
                TrapTapTheme(darkTheme = state.isDarkMode) {
                    // یک Scaffold کلی برای کل اپلیکیشن (بجز اسپلش و بازی)
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
                                        onBack = { viewModel.navigateTo(Screen.Levels) },
                                        onOptionSelected = { opt, isCorrect -> viewModel.onOptionSelected(opt, isCorrect) },
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
}
