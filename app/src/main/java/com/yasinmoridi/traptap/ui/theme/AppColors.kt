package com.yasinmoridi.traptap.ui.theme

import androidx.compose.ui.graphics.Color

object AppColors {
    // Primary Accents
    val PurpleAccent = Color(0xFF7C4DFF)
    val OrangeAccent = Color(0xFFFF6D3F)
    val AmberAccent = Color(0xFFFFB300)
    val AmberDeep = Color(0xFFF9A825)
    val SuccessGreen = Color(0xFF4CAF50)
    val SuccessGreenDark = Color(0xFF2E7D32)
    val SuccessGreenLight = Color(0xFF81C784)
    val ErrorRed = Color(0xFFF44336)
    val NeutralGray = Color(0xFF9E9E9E)

    // Dark Theme
    object Dark {
        val Surface = Color(0xFF1C1B1F)
        val Card = Color(0xFF2D2B33)
        val TextPrimary = Color(0xFFE6E1E5)
        val TextSecondary = Color(0xFFE6E1E5).copy(alpha = 0.55f)
        val HintText = Color(0xFFFFD54F)
        val SuccessBg = Color(0xFF4CAF50).copy(alpha = 0.15f)
        val ErrorBg = Color(0xFFF44336).copy(alpha = 0.12f)
        val Border = Color.White.copy(alpha = 0.08f)
        val BottomItemBg = Color.White.copy(alpha = 0.06f)
    }

    // Light Theme
    object Light {
        val Surface = Color(0xFFFFFBFE)
        val Card = Color.White
        val TextPrimary = Color(0xFF1C1B1F)
        val TextSecondary = Color(0xFF1C1B1F).copy(alpha = 0.5f)
        val HintText = Color(0xFFE65100)
        val SuccessBg = Color(0xFF4CAF50).copy(alpha = 0.15f)
        val ErrorBg = Color(0xFFF44336).copy(alpha = 0.12f)
        val Border = Color.Black.copy(alpha = 0.08f)
        val BottomItemBg = Color.Black.copy(alpha = 0.05f)
        val OptionBg = Color(0xFFF5F0FA)
    }
}
