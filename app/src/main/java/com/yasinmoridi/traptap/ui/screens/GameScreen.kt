package com.yasinmoridi.traptap.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yasinmoridi.traptap.ui.LevelData
import com.yasinmoridi.traptap.ui.levels.Level10Trap
import com.yasinmoridi.traptap.ui.levels.Level11Trap
import com.yasinmoridi.traptap.ui.levels.Level12Trap
import com.yasinmoridi.traptap.ui.levels.Level1Trap
import com.yasinmoridi.traptap.ui.levels.Level2Trap
import com.yasinmoridi.traptap.ui.levels.Level3Trap
import com.yasinmoridi.traptap.ui.levels.Level4Trap
import com.yasinmoridi.traptap.ui.levels.Level5Trap
import com.yasinmoridi.traptap.ui.levels.Level6Trap
import com.yasinmoridi.traptap.ui.levels.Level7Trap
import com.yasinmoridi.traptap.ui.levels.Level8Trap
import com.yasinmoridi.traptap.ui.levels.Level9Trap
import com.yasinmoridi.traptap.ui.levels.util.LevelAction
import com.yasinmoridi.traptap.ui.theme.AppColors
import com.yasinmoridi.traptap.ui.util.AppStrings
import com.yasinmoridi.traptap.util.AppConstants

//صفحه اصلی بازی
@Composable
fun GameScreen(
    strings: AppStrings,
    level: LevelData?,
    isDark: Boolean,
    selectedOption: String?,
    isAnswered: Boolean,
    showHint: Boolean,
    trollMessageIndex: Int,
    showSuccessDialog: Boolean,
    exitButtonOffset: Pair<Float, Float>,
    sliderValue: Float,
    questionOffset: Pair<Float, Float>,
    timer: Int,
    buttonTapCount: Int,
    holdProgress: Float,
    pinchScale: Float,
    onBack: () -> Unit,
    onAction: (LevelAction) -> Unit,
    onToggleHint: () -> Unit,
    onRestart: () -> Unit,
    modifier: Modifier = Modifier
) {
    // انتخاب رنگ‌ها بر اساس تم جاری (تیره/روشن)
    val textPrimary = if (isDark) AppColors.Dark.TextPrimary else AppColors.Light.TextPrimary
    val textSecondary = if (isDark) AppColors.Dark.TextSecondary else AppColors.Light.TextSecondary
    val surfaceColor = if (isDark) AppColors.Dark.Surface else AppColors.Light.Surface
    val cardSurface = if (isDark) AppColors.Dark.Card else AppColors.Light.Card

    Box(modifier = modifier.fillMaxSize().background(surfaceColor)) {
        Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
            GameAppBar(strings, level?.id ?: 0, textPrimary, textSecondary, onBack, onRestart)

            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                // نمایش محتوای اختصاصی برای هر مرحله (تله‌های ترول)
                when (level?.id) {
                    1 -> Level1Trap(strings, textPrimary, cardSurface, isDark, showHint, selectedOption, isAnswered) { opt, isCorr -> 
                        onAction(LevelAction.OptionSelected(opt, isCorr)) 
                    }
                    2 -> Level2Trap(strings, textPrimary, exitButtonOffset) { onAction(LevelAction.MoveExitButton) }
                    3 -> Level3Trap(strings, textPrimary, sliderValue, { onAction(LevelAction.SliderChanged(it)) }) { onAction(LevelAction.OptionSelected("", true)) }
                    4 -> Level4Trap(strings, textPrimary, cardSurface, isDark, questionOffset) { dx, dy -> onAction(LevelAction.Dragged(dx, dy)) }
                    5 -> Level5Trap(strings, textPrimary, timer) { onAction(LevelAction.TimerStarted) }
                    6 -> Level6Trap(strings, textPrimary) { opt, isCorr -> onAction(LevelAction.OptionSelected(opt, isCorr)) }
                    7 -> Level7Trap(strings, textPrimary)
                    8 -> Level8Trap(strings, textPrimary)
                    9 -> Level9Trap(strings, textPrimary)
                    10 -> Level10Trap(strings, textPrimary, holdProgress) { onAction(LevelAction.HoldProgress(it)) }
                    11 -> Level11Trap(strings, textPrimary, pinchScale) { onAction(LevelAction.Pinch(it)) }
                    12 -> Level12Trap(strings, textPrimary, buttonTapCount) { onAction(LevelAction.TiredButtonClick) }
                    else -> {
                        // سایر مراحل استاندارد
                        Level1Trap(strings, textPrimary, cardSurface, isDark, showHint, selectedOption, isAnswered) { opt, isCorr -> 
                            onAction(LevelAction.OptionSelected(opt, isCorr))
                        }
                    }
                }
            }

            val correctKey = AppConstants.OPTION_KEYS[AppConstants.CORRECT_OPTION_INDEX]
            TrollMessageArea(strings, isAnswered, selectedOption == correctKey, trollMessageIndex, isDark, textSecondary)
            GameBottomActions(strings, isDark, textSecondary, showHint, onBack, onToggleHint, onRestart)
        }

        // نمایش دیالوگ پیروزی در صورت برنده شدن
        if (showSuccessDialog) {
            SuccessDialog(strings) { onAction(LevelAction.NextLevel) }
        }
    }
}

//دیالوگ پیروزی که بعد از حل هر مرحله نمایش داده می‌شود
@Composable
fun SuccessDialog(strings: AppStrings, onNext: () -> Unit) {
    AlertDialog(
        onDismissRequest = { },
        title = { 
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text(AppConstants.ICON_VICTORY, fontSize = 40.sp)
                Text(strings.victoryTitle, fontWeight = FontWeight.Black)
            }
        },
        text = { 
            Text(
                strings.victoryMessage,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            ) 
        },
        confirmButton = {
            Button(
                onClick = onNext,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.PurpleAccent)
            ) {
                Text(strings.nextLevel)
            }
        }
    )
}

@Composable
fun GameAppBar(strings: AppStrings, levelId: Int, textPrimary: Color, textSecondary: Color, onBack: () -> Unit, onRestart: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onBack) { Text(AppConstants.ICON_BACK, fontSize = 24.sp, color = textPrimary) }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("${strings.levelPrefix} $levelId", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = textSecondary)
            Text(strings.trapLevelLabel, fontSize = 16.sp, fontWeight = FontWeight.Black, color = textPrimary)
        }
        IconButton(onClick = onRestart) { Text(AppConstants.ICON_RESTART, fontSize = 20.sp, color = textPrimary) }
    }
}

//کارت نمایش سوال مرحله
@Composable
fun PuzzleCard(strings: AppStrings, textColor: Color, surface: Color, isDark: Boolean) {
    Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(32.dp)).background(surface).border(1.dp, if (isDark) AppColors.Dark.Border else AppColors.Light.Border, RoundedCornerShape(32.dp)).padding(24.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier.size(52.dp).background(AppColors.PurpleAccent.copy(alpha = 0.1f), RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) { Text(AppConstants.ICON_DEFAULT_PUZZLE, fontSize = 26.sp) }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = strings.question, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = textColor, textAlign = TextAlign.Center, lineHeight = 24.sp)
        }
    }
}

//جعبه نمایش راهنمای مرحله
@Composable
fun HintBox(hintText: String, isDark: Boolean) {
    Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(AppColors.AmberAccent.copy(alpha = 0.12f)).border(1.5.dp, AppColors.AmberAccent.copy(alpha = 0.35f), RoundedCornerShape(20.dp)).padding(16.dp), verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(AppConstants.ICON_HINT, fontSize = 18.sp)
        Text(text = hintText, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = if (isDark) Color.White else Color.Black, modifier = Modifier.weight(1f))
    }
}

// لیست گزینه‌های پاسخ برای مراحل استاندارد
@Composable
fun OptionsList(options: List<String>, selectedOption: String?, isAnswered: Boolean, textPrimary: Color, isDark: Boolean, onOptionSelected: (String, Boolean) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        options.forEachIndexed { index, text ->
            val key = AppConstants.OPTION_KEYS[index]
            val isSelected = selectedOption == key
            val isCorrect = index == AppConstants.CORRECT_OPTION_INDEX
            
            val bg = when { 
                isAnswered && isSelected -> if (isCorrect) AppColors.SuccessGreen.copy(alpha = 0.15f) else AppColors.ErrorRed.copy(alpha = 0.12f)
                !isAnswered && isSelected -> AppColors.PurpleAccent.copy(alpha = 0.15f)
                else -> if (isDark) AppColors.Dark.BottomItemBg else Color.White 
            }
            val border = when { 
                isAnswered && isSelected -> if (isCorrect) AppColors.SuccessGreen else AppColors.ErrorRed
                !isAnswered && isSelected -> AppColors.PurpleAccent
                else -> if (isDark) AppColors.Dark.Border else AppColors.PurpleAccent.copy(alpha = 0.15f) 
            }
            
            Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(bg).border(1.5.dp, border, RoundedCornerShape(20.dp)).clickable(enabled = !isAnswered) { onOptionSelected(key, isCorrect) }.padding(14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(modifier = Modifier.size(32.dp).background(if (isAnswered && isSelected) (if (isCorrect) AppColors.SuccessGreen else AppColors.ErrorRed) else AppColors.PurpleAccent.copy(alpha = 0.15f), RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) { 
                    Text(if (isAnswered && isSelected) (if (isCorrect) AppConstants.ICON_CORRECT else AppConstants.ICON_WRONG) else key, fontWeight = FontWeight.Black, fontSize = 13.sp, color = if (isAnswered && isSelected) Color.White else AppColors.PurpleAccent) 
                }
                Text(text, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = textPrimary)
            }
        }
    }
}

//ناحیه نمایش پیام‌های تمسخرآمیز ترول
@Composable
fun TrollMessageArea(strings: AppStrings, isAnswered: Boolean, isCorrect: Boolean, trollMsgIdx: Int, isDark: Boolean, textSecondary: Color) {
    val bg = when { 
        isAnswered -> if (isCorrect) AppColors.SuccessGreen.copy(alpha = 0.1f) else AppColors.ErrorRed.copy(alpha = 0.1f)
        else -> if (isDark) AppColors.Dark.BottomItemBg else AppColors.PurpleAccent.copy(alpha = 0.06f) 
    }
    val border = if (isAnswered) (if (isCorrect) AppColors.SuccessGreen.copy(alpha = 0.3f) else AppColors.ErrorRed.copy(alpha = 0.3f)) else Color.Transparent
    
    Box(modifier = Modifier.padding(16.dp).fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(bg).border(1.5.dp, border, RoundedCornerShape(20.dp)).padding(12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(AppConstants.ICON_TROLL, fontSize = 22.sp)
            Text(
                text = if (isAnswered) (if (isCorrect) strings.correctMsg else strings.trollMessages[trollMsgIdx]) else strings.trollWatching, 
                fontSize = 13.sp, 
                fontWeight = FontWeight.Bold, 
                color = if (isAnswered) (if (isCorrect) AppColors.SuccessGreenLight else AppColors.ErrorRed) else textSecondary, 
                lineHeight = 18.sp
            )
        }
    }
}

//اکشن‌های پایین صفحه بازی
@Composable
fun GameBottomActions(strings: AppStrings, isDark: Boolean, textSecondary: Color, showHint: Boolean, onHome: () -> Unit, onHint: () -> Unit, onRestart: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp), horizontalArrangement = Arrangement.SpaceAround) {
        GameBottomItem(strings.home, AppConstants.ICON_HOME, false, isDark, textSecondary, onHome)
        GameBottomItem(strings.hint, AppConstants.ICON_HINT, showHint, isDark, textSecondary, onHint)
        GameBottomItem(strings.restart, AppConstants.ICON_RESTART, false, isDark, textSecondary, onRestart)
    }
}

//آیتم‌های تکی در نوار اکشن پایین
@Composable
fun GameBottomItem(label: String, icon: String, active: Boolean, isDark: Boolean, textSecondary: Color, onClick: () -> Unit) {
    Column(modifier = Modifier.clickable { onClick() }, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Box(modifier = Modifier.size(width = 44.dp, height = 32.dp).background(if (active) AppColors.AmberAccent.copy(alpha = 0.15f) else (if (isDark) AppColors.Dark.BottomItemBg else Color(0xFFF5F5F5)), RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) { Text(icon, fontSize = 16.sp) }
        Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = if (active) AppColors.AmberDeep else textSecondary)
    }
}
