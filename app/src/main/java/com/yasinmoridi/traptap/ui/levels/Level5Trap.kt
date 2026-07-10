package com.yasinmoridi.traptap.ui.levels

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.yasinmoridi.traptap.ui.theme.AppColors
import com.yasinmoridi.traptap.ui.util.AppStrings

//تله مرحله ۵: صبر کردن تا اتمام تایمر (تست صبر کاربر)
@Composable
fun Level5Trap(strings: AppStrings, textColor: Color, timer: Int, onStart: () -> Unit) {
    LaunchedEffect(Unit) { onStart() }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(strings.waitForIt, color = textColor, fontSize = 24.sp, fontWeight = FontWeight.Black)
            Text(timer.toString(), color = textColor, fontSize = 60.sp, fontWeight = FontWeight.ExtraBold)
            if (timer == 0) {
                Text(strings.patienceIsKey, color = AppColors.SuccessGreen, fontWeight = FontWeight.Bold)
            }
        }
    }
}
