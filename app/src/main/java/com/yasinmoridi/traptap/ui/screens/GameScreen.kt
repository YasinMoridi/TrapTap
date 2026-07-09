package com.yasinmoridi.traptap.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import com.yasinmoridi.traptap.ui.theme.PurpleAccent
import com.yasinmoridi.traptap.ui.util.AppStrings
import kotlin.math.roundToInt

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
    onBack: () -> Unit,
    onOptionSelected: (String, Boolean) -> Unit,
    onToggleHint: () -> Unit,
    onRestart: () -> Unit,
    onNextLevel: () -> Unit,
    onMoveExitButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    val textPrimary = if (isDark) Color(0xFFE6E1E5) else Color(0xFF1C1B1F)
    val textSecondary = if (isDark) Color(0xFFE6E1E5).copy(alpha = 0.55f) else Color(0xFF1C1B1F).copy(alpha = 0.5f)
    val surfaceColor = if (isDark) Color(0xFF1C1B1F) else Color(0xFFFFFBFE)
    val cardSurface = if (isDark) Color(0xFF2D2B33) else Color.White

    Box(modifier = modifier.fillMaxSize().background(surfaceColor)) {
        Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
            GameAppBar(strings, level?.id ?: 0, textPrimary, textSecondary, isDark, onBack, onRestart)

            if (level?.id == 2) {
                Level2Trap(strings, textPrimary, exitButtonOffset, onMoveExitButton)
            } else {
                Column(
                    modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    PuzzleCard(strings, textPrimary, cardSurface, isDark)
                    if (showHint) HintBox(strings.hintText, isDark)
                    OptionsList(strings.options, selectedOption, isAnswered, textPrimary, isDark, onOptionSelected)
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
fun SuccessDialog(strings: AppStrings, onNext: () -> Unit) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(if (strings.appName == "TrollMind") "Victory!" else "تبریک!") },
        text = { Text(if (strings.appName == "TrollMind") "You outsmarted the troll... for now." else "فعلاً تونستی از پس ترول بربیای!") },
        confirmButton = {
            Button(onClick = onNext) {
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
    Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(32.dp)).background(surface).border(1.dp, if (isDark) Color.White.copy(alpha = 0.06f) else PurpleAccent.copy(alpha = 0.1f), RoundedCornerShape(32.dp)).padding(24.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier.size(52.dp).background(PurpleAccent.copy(alpha = 0.1f), RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) { Text("🧩", fontSize = 26.sp) }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = strings.question, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = textColor, textAlign = TextAlign.Center, lineHeight = 24.sp)
        }
    }
}

@Composable
fun HintBox(hintText: String, isDark: Boolean) {
    Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(Color(0xFFFFB300).copy(alpha = 0.12f)).border(1.5.dp, Color(0xFFFFB300).copy(alpha = 0.35f), RoundedCornerShape(20.dp)).padding(16.dp), verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("💡", fontSize = 18.sp)
        Text(text = hintText, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = if (isDark) Color(0xFFFFD54F) else Color(0xFFE65100), modifier = Modifier.weight(1f))
    }
}

@Composable
fun OptionsList(options: List<String>, selectedOption: String?, isAnswered: Boolean, textPrimary: Color, isDark: Boolean, onOptionSelected: (String, Boolean) -> Unit) {
    val optionKeys = listOf("A", "B", "C", "D")
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        options.forEachIndexed { index, text ->
            val key = optionKeys[index]; val isSelected = selectedOption == key; val isCorrect = key == "A"
            val bg = when { isAnswered && isSelected -> if (isCorrect) Color(0xFF4CAF50).copy(alpha = 0.15f) else Color(0xFFF44336).copy(alpha = 0.12f); !isAnswered && isSelected -> PurpleAccent.copy(alpha = 0.15f); else -> if (isDark) Color.White.copy(alpha = 0.06f) else Color(0xFFF5F0FA) }
            val border = when { isAnswered && isSelected -> if (isCorrect) Color(0xFF4CAF50) else Color(0xFFF44336); !isAnswered && isSelected -> PurpleAccent; else -> if (isDark) Color.White.copy(alpha = 0.1f) else PurpleAccent.copy(alpha = 0.15f) }
            Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(bg).border(1.5.dp, border, RoundedCornerShape(20.dp)).clickable(enabled = !isAnswered) { onOptionSelected(key, isCorrect) }.padding(14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(modifier = Modifier.size(32.dp).background(if (isAnswered && isSelected) (if (isCorrect) Color(0xFF4CAF50) else Color(0xFFF44336)) else PurpleAccent.copy(alpha = 0.15f), RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) { Text(if (isAnswered && isSelected) (if (isCorrect) "✓" else "✗") else key, fontWeight = FontWeight.Black, fontSize = 13.sp, color = if (isAnswered && isSelected) Color.White else PurpleAccent) }
                Text(text, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = textPrimary)
            }
        }
    }
}

@Composable
fun TrollMessageArea(strings: AppStrings, isAnswered: Boolean, isCorrect: Boolean, trollMsgIdx: Int, isDark: Boolean, textSecondary: Color) {
    val bg = when { isAnswered -> if (isCorrect) Color(0xFF4CAF50).copy(alpha = 0.1f) else Color(0xFFFF6D3F).copy(alpha = 0.1f); else -> if (isDark) Color.White.copy(alpha = 0.05f) else PurpleAccent.copy(alpha = 0.06f) }
    val border = if (isAnswered) (if (isCorrect) Color(0xFF4CAF50).copy(alpha = 0.3f) else Color(0xFFFF6D3F).copy(alpha = 0.3f)) else Color.Transparent
    Box(modifier = Modifier.padding(16.dp).fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(bg).border(1.5.dp, border, RoundedCornerShape(20.dp)).padding(12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("😈", fontSize = 22.sp); Text(text = if (isAnswered) (if (isCorrect) strings.correctMsg else strings.trollMessages[trollMsgIdx]) else strings.trollWatching, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = if (isAnswered) (if (isCorrect) (if (isDark) Color(0xFF81C784) else Color(0xFF2E7D32)) else Color(0xFFFF6D3F)) else textSecondary, lineHeight = 18.sp)
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
        Box(modifier = Modifier.size(width = 44.dp, height = 32.dp).background(if (active) Color(0xFFFFB300).copy(alpha = 0.15f) else (if (isDark) Color.White.copy(alpha = 0.06f) else Color.Black.copy(alpha = 0.05f)), RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) { Text(icon, fontSize = 16.sp) }
        Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = if (active) Color(0xFFF9A825) else textSecondary)
    }
}
