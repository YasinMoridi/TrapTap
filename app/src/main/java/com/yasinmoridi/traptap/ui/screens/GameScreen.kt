package com.yasinmoridi.traptap.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yasinmoridi.traptap.ui.LevelData
import com.yasinmoridi.traptap.ui.theme.AppColors
import com.yasinmoridi.traptap.ui.theme.PurpleAccent
import com.yasinmoridi.traptap.ui.util.AppStrings
import kotlin.math.roundToInt

import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable

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
    onOptionSelected: (String, Boolean) -> Unit,
    onToggleHint: () -> Unit,
    onRestart: () -> Unit,
    onNextLevel: () -> Unit,
    onMoveExitButton: () -> Unit,
    onUpdateSlider: (Float) -> Unit,
    onUpdateQuestionOffset: (Float, Float) -> Unit,
    onStartTimer: () -> Unit,
    onTiredButtonClick: () -> Unit,
    onUpdateHoldProgress: (Float) -> Unit,
    onPinch: (Float) -> Unit,
    onWinLevel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val textPrimary = if (isDark) AppColors.Dark.TextPrimary else AppColors.Light.TextPrimary
    val textSecondary = if (isDark) AppColors.Dark.TextSecondary else AppColors.Light.TextSecondary
    val surfaceColor = if (isDark) AppColors.Dark.Surface else AppColors.Light.Surface
    val cardSurface = if (isDark) AppColors.Dark.Card else AppColors.Light.Card

    Box(modifier = modifier.fillMaxSize().background(surfaceColor)) {
        Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
            GameAppBar(strings, level?.id ?: 0, textPrimary, textSecondary, isDark, onBack, onRestart)

            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                when (level?.id) {
                    2 -> Level2Trap(strings, textPrimary, exitButtonOffset, onMoveExitButton)
                    3 -> Level3Trap(strings, textPrimary, sliderValue, onUpdateSlider, onWinLevel)
                    4 -> Level4Trap(strings, textPrimary, cardSurface, isDark, questionOffset, onUpdateQuestionOffset)
                    5 -> Level5Trap(strings, textPrimary, timer, onStartTimer)
                    6 -> Level6Trap(strings, textPrimary, onOptionSelected)
                    7 -> Level7Trap(strings, textPrimary)
                    8 -> Level8Trap(strings, textPrimary)
                    9 -> Level9Trap(strings, textPrimary)
                    10 -> Level10Trap(strings, textPrimary, holdProgress, onUpdateHoldProgress)
                    11 -> Level11Trap(strings, textPrimary, pinchScale, onPinch)
                    12 -> Level12Trap(strings, textPrimary, buttonTapCount, onTiredButtonClick)
                    else -> {
                        Column(
                            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            PuzzleCard(strings, textPrimary, cardSurface, isDark)
                            if (showHint) HintBox(strings.hintText, isDark)
                            OptionsList(strings.options, selectedOption, isAnswered, textPrimary, isDark, onOptionSelected)
                        }
                    }
                }
            }

            TrollMessageArea(strings, isAnswered, isAnswered && selectedOption == "A", trollMessageIndex, isDark, textSecondary)
            GameBottomActions(strings, isDark, textSecondary, showHint, onBack, onToggleHint, onRestart)
        }

        if (showSuccessDialog) {
            SuccessDialog(strings, onNextLevel)
        }
    }
}

@Composable
fun Level2Trap(strings: AppStrings, textColor: Color, offset: Pair<Float, Float>, onMove: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = if (strings.appName == "TrollMind") "EXIT THE APP TO WIN" else "برای برنده شدن از برنامه خارج شو",
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = textColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { /* User can't click anyway */ },
                modifier = Modifier
                    .offset { IntOffset(offset.first.roundToInt(), offset.second.roundToInt()) }
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                awaitPointerEvent()
                                onMove()
                            }
                        }
                    }
            ) {
                Text(if (strings.appName == "TrollMind") "EXIT" else "خروج")
            }
        }
    }
}

@Composable
fun Level3Trap(strings: AppStrings, textColor: Color, sliderValue: Float, onUpdate: (Float) -> Unit, onWin: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Text(if (strings.appName == "TrollMind") "Reveal the next level..." else "مرحله بعد رو ظاهر کن...", color = textColor, fontWeight = FontWeight.Bold)
            Slider(
                value = sliderValue,
                onValueChange = onUpdate,
                modifier = Modifier.padding(horizontal = 40.dp)
            )
            if (sliderValue > 0.8f) {
                Button(onClick = onWin) { Text("NEXT LEVEL") }
            }
        }
    }
}

@Composable
fun Level4Trap(strings: AppStrings, textColor: Color, surface: Color, isDark: Boolean, offset: Pair<Float, Float>, onUpdate: (Float, Float) -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        // The hidden answer behind the card
        Text("ANSWER: 42", color = textColor.copy(alpha = 0.4f), fontSize = 34.sp, fontWeight = FontWeight.Black)
        
        Box(
            modifier = Modifier
                .offset { IntOffset(offset.first.roundToInt(), offset.second.roundToInt()) }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        // Send the delta (dragAmount) instead of absolute position to avoid jitter
                        onUpdate(dragAmount.x, dragAmount.y)
                    }
                }
        ) {
            PuzzleCard(strings, textColor, surface, isDark)
        }
    }
}

@Composable
fun Level5Trap(strings: AppStrings, textColor: Color, timer: Int, onStart: () -> Unit) {
    LaunchedEffect(Unit) { onStart() }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("WAIT FOR IT...", color = textColor, fontSize = 24.sp, fontWeight = FontWeight.Black)
            Text(timer.toString(), color = textColor, fontSize = 60.sp, fontWeight = FontWeight.ExtraBold)
            if (timer == 0) {
                Text("PATIENCE IS KEY", color = Color.Green, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun Level6Trap(strings: AppStrings, textColor: Color, onSelected: (String, Boolean) -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("THIS IS TOO HARD FOR YOU", color = textColor, fontWeight = FontWeight.Bold)
            Button(onClick = { onSelected("GiveUp", true) }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                Text("GIVE UP")
            }
        }
    }
}

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
            Text(if (strings.appName == "TrollMind") "DON'T LET GO FOR 10s" else "۱۰ ثانیه دستتو برندار", color = textColor, fontWeight = FontWeight.Black)
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(if (isPressing) Color.Red else Color.Gray)
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

@Composable
fun Level12Trap(strings: AppStrings, textColor: Color, count: Int, onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val buttonOffset = if (count > 5) (count - 5) * 20 else 0
        Button(
            onClick = onClick,
            modifier = Modifier.offset(y = buttonOffset.dp)
        ) {
            Text(if (count < 8) "TAP ME" else "I'M TIRED! STOP!")
        }
    }
}

@Composable
fun Level7Trap(strings: AppStrings, textColor: Color) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                if (strings.appName == "TrollMind") "I FEEL DIZZY... TURN ME OVER" else "سرم گیج میره... منو برعکس کن",
                color = textColor,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text("🙃", fontSize = 50.sp)
        }
    }
}

@Composable
fun Level8Trap(strings: AppStrings, textColor: Color) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                if (strings.appName == "TrollMind") "CAN'T HEAR YOU! LOUDER!" else "صدات نمیاد! بلندتر!",
                color = textColor,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text("🔊", fontSize = 50.sp)
        }
    }
}

@Composable
fun Level9Trap(strings: AppStrings, textColor: Color) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                if (strings.appName == "TrollMind") "IT'S TOO DARK IN HERE..." else "اینجا خیلی تاریکه...",
                color = textColor,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text("☀️", fontSize = 50.sp)
        }
    }
}

@Composable
fun Level11Trap(strings: AppStrings, textColor: Color, scale: Float, onScale: (Float) -> Unit) {
    val state = rememberTransformableState { zoomChange, _, _ ->
        onScale(scale * zoomChange)
    }
    
    Box(modifier = Modifier.fillMaxSize().transformable(state = state), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                if (strings.appName == "TrollMind") "STRETCH THE DOOR TO OPEN" else "درو بکش تا باز بشه",
                color = textColor,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.size((100 * scale).dp).background(AppColors.PurpleAccent, RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) {
                Text("🚪", fontSize = (40 * scale).sp)
            }
        }
    }
}

@Composable
fun SuccessDialog(strings: AppStrings, onNext: () -> Unit) {
    AlertDialog(
        onDismissRequest = { },
        title = { 
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text("🎉", fontSize = 40.sp)
                Text(if (strings.appName == "TrollMind") "VICTORY!" else "پیروزی!", fontWeight = FontWeight.Black)
            }
        },
        text = { 
            Text(
                if (strings.appName == "TrollMind") "You outsmarted the troll! He's not happy about it." 
                else "ترول رو شکست دادی! اصلاً خوشحال نیست.",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            ) 
        },
        confirmButton = {
            Button(
                onClick = onNext,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(if (strings.appName == "TrollMind") "Next Level" else "مرحله بعد")
            }
        }
    )
}

@Composable
fun GameAppBar(strings: AppStrings, levelId: Int, textPrimary: Color, textSecondary: Color, isDark: Boolean, onBack: () -> Unit, onRestart: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onBack) { Text("←", fontSize = 24.sp, color = textPrimary) }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("${strings.levelPrefix} $levelId", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = textSecondary)
            Text("Trap Level", fontSize = 16.sp, fontWeight = FontWeight.Black, color = textPrimary)
        }
        IconButton(onClick = onRestart) { Text("↺", fontSize = 20.sp, color = textPrimary) }
    }
}

@Composable
fun PuzzleCard(strings: AppStrings, textColor: Color, surface: Color, isDark: Boolean) {
    Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(32.dp)).background(surface).border(1.dp, if (isDark) AppColors.Dark.Border else AppColors.PurpleAccent.copy(alpha = 0.1f), RoundedCornerShape(32.dp)).padding(24.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier.size(52.dp).background(AppColors.PurpleAccent.copy(alpha = 0.1f), RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) { Text("🧩", fontSize = 26.sp) }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = strings.question, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = textColor, textAlign = TextAlign.Center, lineHeight = 24.sp)
        }
    }
}

@Composable
fun HintBox(hintText: String, isDark: Boolean) {
    Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(AppColors.AmberAccent.copy(alpha = 0.12f)).border(1.5.dp, AppColors.AmberAccent.copy(alpha = 0.35f), RoundedCornerShape(20.dp)).padding(16.dp), verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("💡", fontSize = 18.sp)
        Text(text = hintText, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = if (isDark) AppColors.Dark.HintText else AppColors.Light.HintText, modifier = Modifier.weight(1f))
    }
}

@Composable
fun OptionsList(options: List<String>, selectedOption: String?, isAnswered: Boolean, textPrimary: Color, isDark: Boolean, onOptionSelected: (String, Boolean) -> Unit) {
    val optionKeys = listOf("A", "B", "C", "D")
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        options.forEachIndexed { index, text ->
            val key = optionKeys[index]; val isSelected = selectedOption == key; val isCorrect = key == "A"
            val bg = when { isAnswered && isSelected -> if (isCorrect) AppColors.SuccessGreen.copy(alpha = 0.15f) else AppColors.OrangeAccent.copy(alpha = 0.12f); !isAnswered && isSelected -> AppColors.PurpleAccent.copy(alpha = 0.15f); else -> if (isDark) AppColors.Dark.BottomItemBg else AppColors.Light.OptionBg }
            val border = when { isAnswered && isSelected -> if (isCorrect) AppColors.SuccessGreen else AppColors.OrangeAccent; !isAnswered && isSelected -> AppColors.PurpleAccent; else -> if (isDark) AppColors.Dark.Border else AppColors.PurpleAccent.copy(alpha = 0.15f) }
            Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(bg).border(1.5.dp, border, RoundedCornerShape(20.dp)).clickable(enabled = !isAnswered) { onOptionSelected(key, isCorrect) }.padding(14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(modifier = Modifier.size(32.dp).background(if (isAnswered && isSelected) (if (isCorrect) AppColors.SuccessGreen else AppColors.OrangeAccent) else AppColors.PurpleAccent.copy(alpha = 0.15f), RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) { Text(if (isAnswered && isSelected) (if (isCorrect) "✓" else "✗") else key, fontWeight = FontWeight.Black, fontSize = 13.sp, color = if (isAnswered && isSelected) Color.White else AppColors.PurpleAccent) }
                Text(text, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = textPrimary)
            }
        }
    }
}

@Composable
fun TrollMessageArea(strings: AppStrings, isAnswered: Boolean, isCorrect: Boolean, trollMsgIdx: Int, isDark: Boolean, textSecondary: Color) {
    val bg = when { isAnswered -> if (isCorrect) AppColors.SuccessGreen.copy(alpha = 0.1f) else AppColors.OrangeAccent.copy(alpha = 0.1f); else -> if (isDark) AppColors.Dark.BottomItemBg else AppColors.PurpleAccent.copy(alpha = 0.06f) }
    val border = if (isAnswered) (if (isCorrect) AppColors.SuccessGreen.copy(alpha = 0.3f) else AppColors.OrangeAccent.copy(alpha = 0.3f)) else Color.Transparent
    Box(modifier = Modifier.padding(16.dp).fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(bg).border(1.5.dp, border, RoundedCornerShape(20.dp)).padding(12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("😈", fontSize = 22.sp); Text(text = if (isAnswered) (if (isCorrect) strings.correctMsg else strings.trollMessages[trollMsgIdx]) else strings.trollWatching, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = if (isAnswered) (if (isCorrect) (if (isDark) AppColors.SuccessGreenLight else AppColors.SuccessGreenDark) else AppColors.OrangeAccent) else textSecondary, lineHeight = 18.sp)
        }
    }
}

@Composable
fun GameBottomActions(strings: AppStrings, isDark: Boolean, textSecondary: Color, showHint: Boolean, onHome: () -> Unit, onHint: () -> Unit, onRestart: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp), horizontalArrangement = Arrangement.SpaceAround) {
        GameBottomItem(strings.home, "🏠", false, isDark, textSecondary, onHome)
        GameBottomItem(strings.hint, "💡", showHint, isDark, textSecondary, onHint)
        GameBottomItem(strings.restart, "↺", false, isDark, textSecondary, onRestart)
    }
}

@Composable
fun GameBottomItem(label: String, icon: String, active: Boolean, isDark: Boolean, textSecondary: Color, onClick: () -> Unit) {
    Column(modifier = Modifier.clickable { onClick() }, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Box(modifier = Modifier.size(width = 44.dp, height = 32.dp).background(if (active) AppColors.AmberAccent.copy(alpha = 0.15f) else (if (isDark) AppColors.Dark.BottomItemBg else AppColors.Light.BottomItemBg), RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) { Text(icon, fontSize = 16.sp) }
        Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = if (active) AppColors.AmberDeep else textSecondary)
    }
}
