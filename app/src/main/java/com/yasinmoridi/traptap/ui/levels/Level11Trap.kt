package com.yasinmoridi.traptap.ui.levels

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yasinmoridi.traptap.ui.theme.AppColors
import com.yasinmoridi.traptap.ui.util.AppStrings

// مرحله ۱۱: کشیدن در با دو انگشت (Pinch zoom)
@Composable
fun Level11Trap(strings: AppStrings, textColor: Color, scale: Float, onScale: (Float) -> Unit) {
    val state = rememberTransformableState { zoomChange, _, _ ->
        onScale(scale * zoomChange)
    }

    Box(modifier = Modifier.fillMaxSize().transformable(state = state), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                strings.stretchDoorMessage,
                color = textColor,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .size((100 * scale).dp)
                    .background(AppColors.PurpleAccent, RoundedCornerShape(16.dp)), 
                contentAlignment = Alignment.Center
            ) {
                Text("🚪", fontSize = (40 * scale).sp)
            }
        }
    }
}
