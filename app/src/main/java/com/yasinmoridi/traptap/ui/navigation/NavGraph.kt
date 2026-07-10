package com.yasinmoridi.traptap.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yasinmoridi.traptap.ui.GameViewModel
import com.yasinmoridi.traptap.ui.levels.util.LevelAction
import com.yasinmoridi.traptap.ui.screens.GameScreen
import com.yasinmoridi.traptap.ui.screens.LevelsScreen
import com.yasinmoridi.traptap.ui.screens.SettingsScreen
import com.yasinmoridi.traptap.ui.screens.SplashScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun SetUpNavGraph(
    navController: NavHostController, // کنترلر اصلی جابجایی بین صفحات
    viewModel: GameViewModel = koinViewModel() // دریافت ویومدل با استفاده از Koin
) {
    // دریافت آخرین وضعیت (State) از ویومدل برای نمایش در صفحات
    val state by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = AppDestination.Splash, // صفحه شروع اپلیکیشن
        enterTransition = { EnterTransition.None }, // حذف انیمیشن‌های ورود پیش‌فرض
        exitTransition = { ExitTransition.None }    // حذف انیمیشن‌های خروج پیش‌فرض
    ) {
        // تعریف مسیر صفحه اسپلش
        composable<AppDestination.Splash> {
            SplashScreen(
                strings = state.strings,
                isDark = state.isDarkMode,
                onFinished = {
                    // بعد از اتمام لودینگ، به صفحه مراحل برو و اسپلش را از پشته حذف کن
                    navController.navigate(AppDestination.Levels) {
                        popUpTo(AppDestination.Splash) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        // تعریف مسیر صفحه لیست مراحل
        composable<AppDestination.Levels> {
            LevelsScreen(
                strings = state.strings,
                levels = state.levels,
                coins = state.coins,
                isDark = state.isDarkMode,
                onLevelClick = { level ->
                    // انتخاب مرحله و رفتن به صفحه بازی
                    viewModel.selectLevel(level)
                    navController.navigate(AppDestination.Game)
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        // تعریف مسیر صفحه اصلی بازی
        composable<AppDestination.Game> {
            GameScreen(
                strings = state.strings,
                level = state.currentLevel,
                isDark = state.isDarkMode,
                selectedOption = state.selectedOption,
                isAnswered = state.isAnswered,
                showHint = state.showHint,
                unlockedHintLevel = state.unlockedHintLevel,
                showHintDialog = state.showHintDialog,
                coins = state.coins,
                hintCost = state.hintCost,
                trollMessageIndex = state.trollMessageIndex,
                victoryTrollMessageIndex = state.victoryTrollMessageIndex,
                showSuccessDialog = state.showSuccessDialog,
                exitButtonOffset = state.exitButtonOffset,
                sliderValue = state.sliderValue,
                questionOffset = state.questionOffset,
                timer = state.timer,
                buttonTapCount = state.buttonTapCount,
                holdProgress = state.holdProgress,
                pinchScale = state.pinchScale,
                onBack = { navController.popBackStack() }, // بازگشت به صفحه قبل
                onAction = { viewModel.handleAction(it) }, // ارسال اکشن‌های بازی به ویومدل
                onToggleHint = { viewModel.handleAction(LevelAction.ToggleHintDialog) },
                onRestart = { viewModel.restartLevel() },
                modifier = Modifier.fillMaxSize()
            )
        }

        // تعریف مسیر صفحه تنظیمات
        composable<AppDestination.Settings> {
            SettingsScreen(
                strings = state.strings,
                isDark = state.isDarkMode,
                onToggleTheme = { viewModel.toggleTheme() },
                onLanguageSelect = { langCode -> viewModel.setLanguage(langCode) },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
