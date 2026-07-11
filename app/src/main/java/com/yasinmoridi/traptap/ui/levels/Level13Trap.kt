package com.yasinmoridi.traptap.ui.levels

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yasinmoridi.traptap.ui.util.AppStrings

@Composable
fun Level13Trap(
    strings: AppStrings,
    textColor: Color,
    onSelected: (String, Boolean) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = strings.whichOneIsBigger,
            color = textColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Elephant - Small visually, but realistically bigger
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Gray.copy(alpha = 0.1f))
                    .clickable { onSelected("E", false) },
                contentAlignment = Alignment.Center
            ) {
                Text("🐘", fontSize = 40.sp)
            }

            // Lion - Big visually, but realistically smaller (Correct in this level)
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Gray.copy(alpha = 0.1f))
                    .clickable { onSelected("L", true) },
                contentAlignment = Alignment.Center
            ) {
                Text("🦁", fontSize = 100.sp)
            }
        }
    }
}
