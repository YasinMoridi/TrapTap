package com.yasinmoridi.traptap.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yasinmoridi.traptap.ui.Screen
import com.yasinmoridi.traptap.ui.theme.PurpleAccent
import com.yasinmoridi.traptap.ui.util.AppStrings

@Composable
fun BottomNavigationBar(
    strings: AppStrings,
    isDark: Boolean,
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit
) {
    val textSecondary = if (isDark) Color(0xFFE6E1E5).copy(alpha = 0.55f) else Color(0xFF1C1B1F).copy(alpha = 0.5f)
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isDark) Color(0xFF1E1C23).copy(alpha = 0.95f) else Color(0xFFFFFBFE).copy(alpha = 0.95f))
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        BottomNavItem(
            label = strings.home,
            icon = "🏠",
            active = currentScreen == Screen.Levels,
            isDark = isDark,
            textSecondary = textSecondary
        ) { onNavigate(Screen.Levels) }

        BottomNavItem(
            label = strings.rewards,
            icon = "🏆",
            active = false,
            isDark = isDark,
            textSecondary = textSecondary
        ) { /* Future feature */ }

        BottomNavItem(
            label = strings.profile,
            icon = "⚙️",
            active = currentScreen == Screen.Settings,
            isDark = isDark,
            textSecondary = textSecondary
        ) { onNavigate(Screen.Settings) }
    }
}

@Composable
fun BottomNavItem(
    label: String,
    icon: String,
    active: Boolean,
    isDark: Boolean,
    textSecondary: Color,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(width = 44.dp, height = 32.dp)
                .background(
                    if (active) PurpleAccent.copy(alpha = if (isDark) 0.25f else 0.12f) else Color.Transparent,
                    RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(icon, fontSize = 18.sp)
        }
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = if (active) PurpleAccent else textSecondary
        )
    }
}
