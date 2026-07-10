package com.yasinmoridi.traptap.ui.levels

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import com.yasinmoridi.traptap.ui.screens.PuzzleCard
import com.yasinmoridi.traptap.ui.util.AppStrings
import kotlin.math.roundToInt

//تله مرحله ۴: کشیدن کارت سوال برای دیدن جواب مخفی پشت آن
@Composable
fun Level4Trap(strings: AppStrings, textColor: Color, surface: Color, isDark: Boolean, offset: Pair<Float, Float>, onUpdate: (Float, Float) -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        // جواب مخفی که زیر کارت قرار دارد
        Text(
            text = strings.answerLabel, 
            color = textColor.copy(alpha = 0.4f), 
            fontSize = 34.sp, 
            fontWeight = FontWeight.Black
        )

        Box(
            modifier = Modifier
                .offset { IntOffset(offset.first.roundToInt(), offset.second.roundToInt()) }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        onUpdate(dragAmount.x, dragAmount.y)
                    }
                }
        ) {
            PuzzleCard(strings, textColor, surface, isDark)
        }
    }
}
