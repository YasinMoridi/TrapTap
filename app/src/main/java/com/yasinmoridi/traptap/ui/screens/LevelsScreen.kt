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
import com.yasinmoridi.traptap.ui.theme.PurpleAccent
import com.yasinmoridi.traptap.ui.util.AppStrings

@Composable
fun LevelsScreen(
    strings: AppStrings,
    levels: List<LevelData>,
    isDark: Boolean,
    onLevelClick: (LevelData) -> Unit,
    modifier: Modifier = Modifier
) {
    val textPrimary = if (isDark) Color(0xFFE6E1E5) else Color(0xFF1C1B1F)
    val textSecondary = if (isDark) Color(0xFFE6E1E5).copy(alpha = 0.55f) else Color(0xFF1C1B1F).copy(alpha = 0.5f)
    val surfaceColor = if (isDark) Color(0xFF1C1B1F) else Color(0xFFFFFBFE)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(surfaceColor)
    ) {
        // Top App Bar
        LevelsTopAppBar(strings, textPrimary, isDark)

        // Progress Banner
        ProgressBanner(strings)

        // Level Grid
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
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(if (isDark) Color.White.copy(alpha = 0.08f) else Color.Black.copy(alpha = 0.06f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text("🧩", fontSize = 18.sp)
        }

        Text(
            text = strings.levelsTitle,
            fontSize = 20.sp,
            fontWeight = FontWeight.Black,
            color = textColor
        )

        Row(
            modifier = Modifier
                .border(
                    width = 1.5.dp,
                    color = Color(0xFFFFB300).copy(alpha = 0.4f),
                    shape = CircleShape
                )
                .background(Color(0xFFFFB300).copy(alpha = if (isDark) 0.15f else 0.12f), CircleShape)
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text("🪙", fontSize = 14.sp)
            Text(strings.coins, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFFF9A825))
        }
    }
}

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
                    Text("🧩", fontSize = 22.sp)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
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

@Composable
fun LevelCard(
    level: LevelData,
    isDark: Boolean,
    textSecondary: Color,
    onLevelClick: (LevelData) -> Unit
) {
    val cardBg = when (level.state) {
        LevelState.Completed -> if (isDark) Color(0xFF4CAF50).copy(alpha = 0.15f) else Color(0xFFF1F8F1)
        LevelState.Current -> PurpleAccent
        LevelState.Locked -> if (isDark) Color.White.copy(alpha = 0.05f) else Color(0xFFF5F0FA)
    }
    
    val borderColor = when (level.state) {
        LevelState.Completed -> Color(0xFF4CAF50).copy(alpha = 0.4f)
        LevelState.Current -> Color.Transparent
        LevelState.Locked -> if (isDark) Color.White.copy(alpha = 0.08f) else Color.Black.copy(alpha = 0.08f)
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
        Text(
            text = if (level.state == LevelState.Locked) "🔒" else level.emoji,
            fontSize = if (level.state == LevelState.Locked) 16.sp else 20.sp
        )
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
        if (level.state == LevelState.Completed && level.stars != null) {
            StarRow(level.stars)
        }
        if (level.state == LevelState.Current) {
            Box(modifier = Modifier.size(6.dp).background(Color.White.copy(alpha = 0.9f), CircleShape))
        }
    }
}

@Composable
fun StarRow(count: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        repeat(3) { i ->
            Text(
                "★", 
                fontSize = 10.sp, 
                color = if (i < count) Color(0xFFFFB300) else Color.Gray.copy(alpha = 0.3f)
            )
        }
    }
}
