package com.yasinmoridi.traptap.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yasinmoridi.traptap.ui.util.AppStrings
import com.yasinmoridi.traptap.ui.util.PersianStrings

@Composable
fun SettingsScreen(
    strings: AppStrings,
    isDark: Boolean,
    onToggleTheme: () -> Unit,
    onToggleLanguage: () -> Unit,
    modifier: Modifier = Modifier
) {
    val textPrimary = if (isDark) Color(0xFFE6E1E5) else Color(0xFF1C1B1F)
    val textSecondary = if (isDark) Color(0xFFE6E1E5).copy(alpha = 0.55f) else Color(0xFF1C1B1F).copy(alpha = 0.5f)
    val surfaceColor = if (isDark) Color(0xFF1C1B1F) else Color(0xFFFFFBFE)
    val cardSurface = if (isDark) Color(0xFF2D2B33) else Color.White

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(surfaceColor)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = strings.settingsTitle,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = textPrimary
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Language Setting
            SettingsCard(cardSurface, isDark) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(strings.languageLabel, fontWeight = FontWeight.Bold, color = textPrimary)
                        Text(
                            text = if (strings == PersianStrings) strings.persian else strings.english,
                            fontSize = 12.sp,
                            color = textSecondary
                        )
                    }
                    Button(onClick = onToggleLanguage) {
                        Text(if (strings == PersianStrings) "English" else "فارسی")
                    }
                }
            }

            // Theme Setting
            SettingsCard(cardSurface, isDark) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(strings.themeLabel, fontWeight = FontWeight.Bold, color = textPrimary)
                        Text(
                            text = if (isDark) strings.darkMode else strings.lightMode,
                            fontSize = 12.sp,
                            color = textSecondary
                        )
                    }
                    Switch(
                        checked = isDark,
                        onCheckedChange = { onToggleTheme() }
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsCard(surface: Color, isDark: Boolean, content: @Composable () -> Unit) {
    Surface(
        color = surface,
        shape = RoundedCornerShape(24.dp),
        shadowElevation = if (isDark) 0.dp else 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(modifier = Modifier.padding(20.dp)) {
            content()
        }
    }
}
