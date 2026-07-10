package com.yasinmoridi.traptap.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yasinmoridi.traptap.ui.LevelData
import com.yasinmoridi.traptap.ui.LevelState
import com.yasinmoridi.traptap.ui.theme.AppColors
import com.yasinmoridi.traptap.ui.util.AppStrings
import com.yasinmoridi.traptap.util.AppConstants


// این صفحه لیستی از تمام مراحل بازی را به صورت شبکه‌ای (Grid) به کاربر نشان می‌دهد

@Composable
fun LevelsScreen(
    strings: AppStrings,
    levels: List<LevelData>,
    isDark: Boolean,
    onLevelClick: (LevelData) -> Unit,
    modifier: Modifier = Modifier
) {
    val textPrimary = if (isDark) AppColors.Dark.TextPrimary else AppColors.Light.TextPrimary
    val textSecondary = if (isDark) AppColors.Dark.TextSecondary else AppColors.Light.TextSecondary
    val surfaceColor = if (isDark) AppColors.Dark.Surface else AppColors.Light.Surface

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(surfaceColor)
    ) {
        // نوار بالای صفحه شامل عنوان و تعداد سکه‌ها
        LevelsTopAppBar(strings, textPrimary, isDark)

        // بنر نمایش میزان پیشرفت کاربر در فصل جاری
        ProgressBanner(strings)

        // نمایش مراحل به صورت شبکه‌ای (Grid) با ۴ ستون
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(levels) { level ->
                LevelCard(level, isDark, textSecondary, onLevelClick)
            }
        }
    }
}

// نوار بالای صفحه لیست مراحل
@Composable
fun LevelsTopAppBar(
    strings: AppStrings,
    textColor: Color,
    isDark: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // آیکون پازل در سمت چپ
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(if (isDark) AppColors.Dark.Border else AppColors.Light.BottomItemBg, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(AppConstants.ICON_DEFAULT_PUZZLE, fontSize = 18.sp)
        }

        // عنوان صفحه (انتخاب مرحله)
        Text(
            text = strings.levelsTitle,
            fontSize = 20.sp,
            fontWeight = FontWeight.Black,
            color = textColor
        )

        // نمایش سکه‌ها در سمت راست
        Row(
            modifier = Modifier
                .border(
                    width = 1.5.dp,
                    color = AppColors.AmberAccent.copy(alpha = 0.4f),
                    shape = CircleShape
                )
                .background(AppColors.AmberAccent.copy(alpha = if (isDark) 0.15f else 0.12f), CircleShape)
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(AppConstants.ICON_COIN, fontSize = 14.sp)
            Text(strings.coins, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = AppColors.AmberDeep)
        }
    }
}

// بنر نمایش پیشرفت (Progress Banner)
@Composable
fun ProgressBanner(strings: AppStrings) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Brush.linearGradient(listOf(Color(0xFF7C4DFF), Color(0xFF5C35CC))))
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(strings.packProgress, fontSize = 11.sp, color = Color.White.copy(alpha = 0.7f), fontWeight = FontWeight.SemiBold)
                    Text(strings.solvedLabel, fontSize = 13.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.White.copy(alpha = 0.15f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(AppConstants.ICON_DEFAULT_PUZZLE, fontSize = 22.sp)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            // نوار پیشرفت (ProgressBar)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .background(Color.White.copy(alpha = 0.2f), CircleShape)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .fillMaxHeight()
                        .background(Color.White.copy(alpha = 0.9f), CircleShape)
                )
            }
            Text("40%", fontSize = 10.sp, color = Color.White.copy(alpha = 0.6f), modifier = Modifier.padding(top = 4.dp))
        }
    }
}

// کارت مربوط به هر مرحله در لیست.
@Composable
fun LevelCard(
    level: LevelData,
    isDark: Boolean,
    textSecondary: Color,
    onLevelClick: (LevelData) -> Unit
) {
    // تعیین رنگ پس‌زمینه کارت بر اساس وضعیت مرحله
    val cardBg = when (level.state) {
        LevelState.Completed -> if (isDark) AppColors.SuccessGreen.copy(alpha = 0.15f) else Color(0xFFF1F8F1)
        LevelState.Current -> AppColors.PurpleAccent
        LevelState.Locked -> if (isDark) AppColors.Dark.BottomItemBg else AppColors.Light.OptionBg
    }
    
    // تعیین رنگ حاشیه کارت
    val borderColor = when (level.state) {
        LevelState.Completed -> AppColors.SuccessGreen.copy(alpha = 0.4f)
        LevelState.Current -> Color.Transparent
        LevelState.Locked -> if (isDark) AppColors.Dark.Border else AppColors.Light.Border
    }

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(cardBg)
            .border(1.5.dp, borderColor, RoundedCornerShape(20.dp))
            .clickable { onLevelClick(level) }
            .padding(vertical = 12.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // نمایش ایموجی مرحله یا آیکون قفل
        Text(
            text = if (level.state == LevelState.Locked) AppConstants.ICON_LOCK else level.emoji,
            fontSize = if (level.state == LevelState.Locked) 16.sp else 20.sp
        )
        // نمایش شماره مرحله
        Text(
            text = level.id.toString(),
            fontSize = 15.sp,
            fontWeight = FontWeight.Black,
            color = when (level.state) {
                LevelState.Current -> Color.White
                LevelState.Completed -> Color(0xFF2E7D32)
                LevelState.Locked -> textSecondary
            }
        )
        // نمایش ستاره‌ها در صورتی که مرحله تمام شده باشد
        if (level.state == LevelState.Completed && level.stars != null) {
            StarRow(level.stars)
        }
        // نشانگر مرحله فعلی
        if (level.state == LevelState.Current) {
            Box(modifier = Modifier.size(6.dp).background(Color.White.copy(alpha = 0.9f), CircleShape))
        }
    }
}

// نمایش ستاره‌های کسب شده در یک مرحله
@Composable
fun StarRow(count: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        repeat(3) { i ->
            Text(
                AppConstants.ICON_STAR,
                fontSize = 10.sp, 
                color = if (i < count) AppColors.AmberAccent else Color.Gray.copy(alpha = 0.3f)
            )
        }
    }
}
