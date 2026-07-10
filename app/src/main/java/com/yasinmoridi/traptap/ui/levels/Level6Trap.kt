package com.yasinmoridi.traptap.ui.levels

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yasinmoridi.traptap.ui.theme.AppColors
import com.yasinmoridi.traptap.ui.util.AppStrings
import com.yasinmoridi.traptap.util.AppConstants

//تله مرحله ۶: دکمه تسلیم (که در واقع جواب درست است!)
@Composable
fun Level6Trap(strings: AppStrings, textColor: Color, onSelected: (String, Boolean) -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(strings.tooHardForYou, color = textColor, fontWeight = FontWeight.Bold)
            Button(
                onClick = { onSelected(AppConstants.ACTION_GIVE_UP, true) }, 
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.ErrorRed)
            ) {
                Text(strings.giveUp)
            }
        }
    }
}
