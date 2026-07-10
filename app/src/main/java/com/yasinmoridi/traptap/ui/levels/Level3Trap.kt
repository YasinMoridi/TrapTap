package com.yasinmoridi.traptap.ui.levels

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yasinmoridi.traptap.ui.theme.AppColors
import com.yasinmoridi.traptap.ui.util.AppStrings

//تله مرحله ۳: استفاده از اسلایدر برای ظاهر کردن دکمه مرحله بعد
@Composable
fun Level3Trap(strings: AppStrings, textColor: Color, sliderValue: Float, onUpdate: (Float) -> Unit, onWin: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Text(strings.revealNextLevel, color = textColor, fontWeight = FontWeight.Bold)
            Slider(
                value = sliderValue,
                onValueChange = onUpdate,
                colors = SliderDefaults.colors(
                    thumbColor = AppColors.PurpleAccent,
                    activeTrackColor = AppColors.PurpleAccent
                ),
                modifier = Modifier.padding(horizontal = 40.dp)
            )
            if (sliderValue > 0.8f) {
                Button(
                    onClick = onWin,
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.PurpleAccent)
                ) { 
                    Text(strings.nextLevel) 
                }
            }
        }
    }
}
