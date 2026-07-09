package com.yasinmoridi.traptap.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TrollMascot(size: Dp = 120.dp, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(size)) {
        val scale = size.toPx() / 120f
        
        // Hair Spikes
        withTransform({
            scale(scale, scale, Offset.Zero)
            rotate(-20f, Offset(35f, 28f))
        }) {
            drawOval(Color(0xFF4CAF50), Offset(27f, 10f), Size(16f, 36f))
        }
        withTransform({ scale(scale, scale, Offset.Zero) }) {
            drawOval(Color(0xFF43A047), Offset(52f, -4f), Size(16f, 44f))
        }
        withTransform({
            scale(scale, scale, Offset.Zero)
            rotate(20f, Offset(85f, 28f))
        }) {
            drawOval(Color(0xFF4CAF50), Offset(77f, 10f), Size(16f, 36f))
        }

        // Body / Head
        withTransform({ scale(scale, scale, Offset.Zero) }) {
            drawOval(Color(0xFFFFD54F), Offset(18f, 34f), Size(84f, 76f))
            
            // Ears
            drawOval(Color(0xFFFFD54F), Offset(8f, 54f), Size(20f, 28f))
            drawOval(Color(0xFFFFD54F), Offset(92f, 54f), Size(20f, 28f))
            drawOval(Color(0xFFFFBC02), Offset(12f, 59f), Size(12f, 18f))
            drawOval(Color(0xFFFFBC02), Offset(96f, 59f), Size(12f, 18f))

            // Eyes
            // Left
            drawOval(Color.White, Offset(33f, 51f), Size(22f, 24f))
            drawOval(Color(0xFF1565C0), Offset(37f, 56.5f), Size(14f, 15f))
            drawOval(Color(0xFF000000), Offset(41.5f, 61.5f), Size(5f, 5f))
            // Right
            drawOval(Color.White, Offset(65f, 51f), Size(22f, 24f))
            drawOval(Color(0xFF1565C0), Offset(69f, 56.5f), Size(14f, 15f))
            drawOval(Color(0xFF000000), Offset(73.5f, 61.5f), Size(5f, 5f))

            // Eyebrows
            val leftBrow = Path().apply {
                moveTo(36f, 54f)
                quadraticBezierTo(44f, 50f, 52f, 53f)
            }
            drawPath(leftBrow, Color(0xFF5D4037), style = Stroke(width = 2.5f, cap = StrokeCap.Round))

            val rightBrow = Path().apply {
                moveTo(68f, 52f)
                quadraticBezierTo(76f, 49f, 84f, 52f)
            }
            drawPath(rightBrow, Color(0xFF5D4037), style = Stroke(width = 2.5f, cap = StrokeCap.Round))

            // Nose
            drawOval(Color(0xFFF9A825), Offset(54f, 71f), Size(12f, 10f))

            // Mouth Smirk
            val mouth = Path().apply {
                moveTo(40f, 88f)
                quadraticBezierTo(52f, 100f, 70f, 96f)
                quadraticBezierTo(80f, 93f, 82f, 88f)
            }
            drawPath(mouth, Color(0xFF5D4037), style = Stroke(width = 2.5f, cap = StrokeCap.Round))
        }
    }
}
