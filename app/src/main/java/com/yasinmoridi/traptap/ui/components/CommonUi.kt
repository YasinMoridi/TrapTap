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
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import com.yasinmoridi.traptap.ui.navigation.AppDestination
import com.yasinmoridi.traptap.ui.theme.PurpleAccent
import com.yasinmoridi.traptap.ui.util.AppStrings

@Composable
fun BottomNavigationBar(
    strings: AppStrings, // رشته‌های متنی ترجمه شده
    isDark: Boolean, // وضعیت حالت تیره/روشن
    currentDestination: NavDestination?, // مقصد فعلی در نویگیشن
    onNavigate: (AppDestination) -> Unit // تابع بازگشتی برای جابجایی
) {
    // تعیین رنگ متن ثانویه بر اساس تم
    val textSecondary = if (isDark) Color(0xFFE6E1E5).copy(alpha = 0.55f) else Color(0xFF1C1B1F).copy(alpha = 0.5f)
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isDark) Color(0xFF1E1C23).copy(alpha = 0.95f) else Color(0xFFFFFBFE).copy(alpha = 0.95f))
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceAround // توزیع یکنواخت دکمه‌ها
    ) {
        // دکمه فروشگاه (سمت چپ)
        BottomNavItem(
            label = strings.shop,
            icon = "💰",
            active = currentDestination?.hasRoute<AppDestination.Shop>() == true,
            isDark = isDark,
            textSecondary = textSecondary
        ) { onNavigate(AppDestination.Shop) }

        // دکمه خانه (لیست مراحل - وسط)
        BottomNavItem(
            label = strings.home,
            icon = "🏠",
            active = currentDestination?.hasRoute<AppDestination.Levels>() == true,
            isDark = isDark,
            textSecondary = textSecondary
        ) { onNavigate(AppDestination.Levels) }

        // دکمه تنظیمات (سمت راست)
        BottomNavItem(
            label = strings.profile,
            icon = "⚙️",
            active = currentDestination?.hasRoute<AppDestination.Settings>() == true,
            isDark = isDark,
            textSecondary = textSecondary
        ) { onNavigate(AppDestination.Settings) }
    }
}

@Composable
fun BottomNavItem(
    label: String, // برچسب متنی
    icon: String, // آیکون (ایموجی)
    active: Boolean, // آیا این آیتم در حال حاضر فعال است؟
    isDark: Boolean, // تم فعلی
    textSecondary: Color, // رنگ پیش‌فرض متن
    onClick: () -> Unit // اکشن کلیک
) {
    Column(
        modifier = Modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // پس‌زمینه آیکون که در حالت فعال رنگی می‌شود
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
        // متن زیر آیکون
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = if (active) PurpleAccent else textSecondary
        )
    }
}
