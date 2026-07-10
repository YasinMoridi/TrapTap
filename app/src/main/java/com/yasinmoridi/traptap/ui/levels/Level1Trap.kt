package com.yasinmoridi.traptap.ui.levels

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yasinmoridi.traptap.ui.screens.OptionsList
import com.yasinmoridi.traptap.ui.screens.PuzzleCard
import com.yasinmoridi.traptap.ui.util.AppStrings


// مرحله ۱:پازل چهارگزینه‌ای
@Composable
fun Level1Trap(
    strings: AppStrings,
    textPrimary: Color,
    cardSurface: Color,
    isDark: Boolean,
    selectedOption: String?,
    isAnswered: Boolean,
    onOptionSelected: (String, Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // نمایش کارت سوال
        PuzzleCard(strings, textPrimary, cardSurface, isDark)
        
        // نمایش لیست گزینه‌ها
        OptionsList(
            options = strings.options,
            selectedOption = selectedOption,
            isAnswered = isAnswered,
            textPrimary = textPrimary,
            isDark = isDark,
            onOptionSelected = onOptionSelected
        )
    }
}
