package com.yasinmoridi.traptap.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yasinmoridi.traptap.ui.theme.AppColors
import com.yasinmoridi.traptap.ui.util.AppStrings
import com.yasinmoridi.traptap.ui.util.getNativeLanguageName
import com.yasinmoridi.traptap.ui.util.i18n.ArabicStrings
import com.yasinmoridi.traptap.ui.util.i18n.ChineseStrings
import com.yasinmoridi.traptap.ui.util.i18n.CzechStrings
import com.yasinmoridi.traptap.ui.util.i18n.DutchStrings
import com.yasinmoridi.traptap.ui.util.i18n.FrenchStrings
import com.yasinmoridi.traptap.ui.util.i18n.GermanStrings
import com.yasinmoridi.traptap.ui.util.i18n.HebrewStrings
import com.yasinmoridi.traptap.ui.util.i18n.HindiStrings
import com.yasinmoridi.traptap.ui.util.i18n.HungarianStrings
import com.yasinmoridi.traptap.ui.util.i18n.IndonesianStrings
import com.yasinmoridi.traptap.ui.util.i18n.ItalianStrings
import com.yasinmoridi.traptap.ui.util.i18n.JapaneseStrings
import com.yasinmoridi.traptap.ui.util.i18n.KoreanStrings
import com.yasinmoridi.traptap.ui.util.i18n.PersianStrings
import com.yasinmoridi.traptap.ui.util.i18n.PolishStrings
import com.yasinmoridi.traptap.ui.util.i18n.PortugueseStrings
import com.yasinmoridi.traptap.ui.util.i18n.RomanianStrings
import com.yasinmoridi.traptap.ui.util.i18n.RussianStrings
import com.yasinmoridi.traptap.ui.util.i18n.SpanishStrings
import com.yasinmoridi.traptap.ui.util.i18n.SwedishStrings
import com.yasinmoridi.traptap.ui.util.i18n.ThaiStrings
import com.yasinmoridi.traptap.ui.util.i18n.TurkishStrings
import com.yasinmoridi.traptap.ui.util.i18n.UkrainianStrings
import com.yasinmoridi.traptap.ui.util.i18n.VietnameseStrings
import com.yasinmoridi.traptap.util.AppConstants


// صفحه تنظیمات برنامه
@Composable
fun SettingsScreen(
    strings: AppStrings,
    isDark: Boolean,
    onToggleTheme: () -> Unit,
    onLanguageSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val textPrimary = if (isDark) AppColors.Dark.TextPrimary else AppColors.Light.TextPrimary
    val textSecondary = if (isDark) AppColors.Dark.TextSecondary else AppColors.Light.TextSecondary
    val surfaceColor = if (isDark) AppColors.Dark.Surface else AppColors.Light.Surface
    val cardSurface = if (isDark) AppColors.Dark.Card else AppColors.Light.Card

    var showLanguageDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(surfaceColor)
    ) {
        // بخش سربرگ (Header)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = strings.settingsTitle,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = textPrimary
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // تنظیمات مربوط به زبان برنامه
            SettingsCard(cardSurface, isDark) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showLanguageDialog = true },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(strings.languageLabel, fontWeight = FontWeight.Bold, color = textPrimary)
                        Text(
                            text = getNativeLanguageName(
                                when (strings) {
                                    PersianStrings -> "fa"
                                    ChineseStrings -> "zh"
                                    RussianStrings -> "ru"
                                    GermanStrings -> "de"
                                    HindiStrings -> "hi"
                                    ArabicStrings -> "ar"
                                    FrenchStrings -> "fr"
                                    SpanishStrings -> "es"
                                    HebrewStrings -> "he"
                                    TurkishStrings -> "tr"
                                    VietnameseStrings -> "vi"
                                    PortugueseStrings -> "pt"
                                    JapaneseStrings -> "ja"
                                    KoreanStrings -> "ko"
                                    ItalianStrings -> "it"
                                    IndonesianStrings -> "id"
                                    ThaiStrings -> "th"
                                    PolishStrings -> "pl"
                                    DutchStrings -> "nl"
                                    UkrainianStrings -> "uk"
                                    CzechStrings -> "cs"
                                    RomanianStrings -> "ro"
                                    HungarianStrings -> "hu"
                                    SwedishStrings -> "sv"
                                    else -> "en"
                                }
                            ),
                            fontSize = 12.sp,
                            color = textSecondary
                        )
                    }
                    // دکمه نمایش لیست زبان‌ها
                    Icon(
                        imageVector = Icons.Default.Language,
                        contentDescription = null,
                        tint = textPrimary
                    )
                }
            }

            // تنظیمات مربوط به تم برنامه (تیره/روشن)
            SettingsCard(cardSurface, isDark) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(strings.themeLabel, fontWeight = FontWeight.Bold, color = textPrimary)
                        Text(
                            text = if (isDark) strings.darkMode else strings.lightMode,
                            fontSize = 12.sp,
                            color = textSecondary
                        )
                    }
                    // سوییچ برای فعال/غیرفعال کردن حالت شب
                    Switch(
                        checked = isDark,
                        onCheckedChange = { onToggleTheme() }
                    )
                }
            }
        }
    }

    // دیالوگ انتخاب زبان
    if (showLanguageDialog) {
        AlertDialog(
            onDismissRequest = { showLanguageDialog = false },
            title = { Text(strings.languageLabel, color = textPrimary) },
            text = {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(AppConstants.AVAILABLE_LANGUAGES) { langCode ->
                        Surface(
                            onClick = {
                                onLanguageSelect(langCode)
                                showLanguageDialog = false
                            },
                            shape = RoundedCornerShape(12.dp),
                            color = Color.Transparent,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = getNativeLanguageName(langCode),
                                modifier = Modifier.padding(16.dp),
                                color = textPrimary,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showLanguageDialog = false }) {
                    Text(strings.cancel)
                }
            },
            containerColor = cardSurface
        )
    }
}

// قالب کلی برای کارت‌های تنظیمات
@Composable
fun SettingsCard(surface: Color, isDark: Boolean, content: @Composable () -> Unit) {
    Surface(
        color = surface,
        shape = RoundedCornerShape(24.dp),
        shadowElevation = if (isDark) 0.dp else 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(modifier = Modifier.padding(20.dp)) {
            content()
        }
    }
}
