package com.yasinmoridi.traptap.ui.levels

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yasinmoridi.traptap.ui.theme.AppColors
import com.yasinmoridi.traptap.ui.util.AppStrings

//تله مرحله ۱۰: نگه داشتن دکمه برای مدت زمان مشخص
@Composable
fun Level10Trap(strings: AppStrings, textColor: Color, progress: Float, onUpdate: (Float) -> Unit) {
    var isPressing by remember { mutableStateOf(false) }
    LaunchedEffect(isPressing) {
        if (isPressing) {
            while (progress < 1f) {
                kotlinx.coroutines.delay(100)
                onUpdate(0.01f)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(strings.dontLetGo, color = textColor, fontWeight = FontWeight.Black)
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(if (isPressing) AppColors.ErrorRed else AppColors.NeutralGray)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                isPressing = true
                                try {
                                    awaitRelease()
                                } finally {
                                    isPressing = false
                                }
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(progress = { progress }, color = Color.White)
            }
        }
    }
}
