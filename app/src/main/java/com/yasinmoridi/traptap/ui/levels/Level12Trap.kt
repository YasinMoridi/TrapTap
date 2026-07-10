package com.yasinmoridi.traptap.ui.levels

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yasinmoridi.traptap.ui.theme.AppColors
import com.yasinmoridi.traptap.ui.util.AppStrings

//تله مرحله ۱۲: دکمه‌ای که بعد از چند بار کلیک، خسته می‌شود و پایین می‌رود
@Composable
fun Level12Trap(strings: AppStrings, textColor: Color, count: Int, onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val buttonOffset = if (count > 5) (count - 5) * 20 else 0
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = AppColors.PurpleAccent),
            modifier = Modifier.offset(y = buttonOffset.dp)
        ) {
            Text(if (count < 8) strings.tapMe else strings.imTired)
        }
    }
}
