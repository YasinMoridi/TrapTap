package com.yasinmoridi.traptap.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yasinmoridi.traptap.ui.GameViewModel
import com.yasinmoridi.traptap.ui.screens.GameScreen
import com.yasinmoridi.traptap.ui.screens.LevelsScreen
import com.yasinmoridi.traptap.ui.screens.SettingsScreen
import com.yasinmoridi.traptap.ui.screens.SplashScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    viewModel: GameViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = AppDestination.Splash,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable<AppDestination.Splash> {
            SplashScreen(
                strings = state.strings,
                isDark = state.isDarkMode,
                onFinished = {
                    navController.navigate(AppDestination.Levels) {
                        popUpTo(AppDestination.Splash) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        composable<AppDestination.Levels> {
            LevelsScreen(
                strings = state.strings,
                levels = state.levels,
                isDark = state.isDarkMode,
                onLevelClick = { level ->
                    viewModel.selectLevel(level)
                    navController.navigate(AppDestination.Game)
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        composable<AppDestination.Game> {
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
                onBack = { navController.popBackStack() },
                onAction = { viewModel.handleAction(it) },
                onToggleHint = { viewModel.toggleHint() },
                onRestart = { viewModel.restartLevel() },
                modifier = Modifier.fillMaxSize()
            )
        }

        composable<AppDestination.Settings> {
            SettingsScreen(
                strings = state.strings,
                isDark = state.isDarkMode,
                onToggleTheme = { viewModel.toggleTheme() },
                onToggleLanguage = { viewModel.toggleLanguage() },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
