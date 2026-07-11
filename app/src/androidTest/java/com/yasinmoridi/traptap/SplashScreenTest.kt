package com.yasinmoridi.traptap

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.yasinmoridi.traptap.ui.screens.SplashScreen
import com.yasinmoridi.traptap.ui.util.i18n.EnglishStrings
import org.junit.Rule
import org.junit.Test

class SplashScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun splashScreen_displaysAppName() {
        composeTestRule.setContent {
            SplashScreen(
                strings = EnglishStrings,
                isDark = false
            )
        }

        composeTestRule.onNodeWithText("TrollMind").assertIsDisplayed()
    }
}
