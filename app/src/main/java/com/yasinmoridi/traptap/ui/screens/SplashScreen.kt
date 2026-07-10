package com.yasinmoridi.traptap.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yasinmoridi.traptap.ui.components.TrollMascot
import com.yasinmoridi.traptap.ui.theme.*
import com.yasinmoridi.traptap.ui.util.AppStrings
import com.yasinmoridi.traptap.util.AppConstants


// این صفحه هنگام باز شدن برنامه نمایش داده می‌شود و شامل انیمیشن‌های لودینگ است

@Composable
fun SplashScreen(
    strings: AppStrings,
    isDark: Boolean,
    onFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    var progress by remember { mutableStateOf(0f) }
    var timeElapsed by remember { mutableStateOf(false) }

    // انیمیشن پالس (ضربان) پشت کاراکتر ترول
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulseScale"
    )
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulseAlpha"
    )

    // انیمیشن شناور بودن (Floating) کاراکتر ترول
    val floatOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float"
    )

    // شبیه‌سازی لودینگ با پیشرفت تدریجی در ۴ ثانیه
    LaunchedEffect(Unit) {
        val animationDuration = 4000
        val startTime = System.currentTimeMillis()
        while (progress < 1f) {
            val currentTime = System.currentTimeMillis()
            progress = ((currentTime - startTime).toFloat() / animationDuration).coerceAtMost(1f)
            kotlinx.coroutines.delay(16)
        }
    }

    // حداقل زمان نمایش صفحه اسپلش (۵ ثانیه)
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(5000)
        timeElapsed = true
    }

    // هدایت به صفحه بعد پس از اتمام لودینگ و زمان مشخص شده
    LaunchedEffect(progress, timeElapsed) {
        if (progress >= 1f && timeElapsed) {
            onFinished()
        }
    }

    val bgColors = if (isDark) {
        listOf(DarkBgStart, DarkBgCenter, DarkBgEnd)
    } else {
        listOf(LightBgStart, LightBgCenter, LightBgEnd)
    }

    val textColor = if (isDark) DarkText else LightText

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.linearGradient(bgColors)),
        contentAlignment = Alignment.Center
    ) {
        // رسم نقاط پس‌زمینه (Pattern)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.1f }
                .drawBehind {
                    val gap = 24.dp.toPx()
                    for (x in 0..size.width.toInt() step gap.toInt()) {
                        for (y in 0..size.height.toInt() step gap.toInt()) {
                            drawCircle(
                                color = if (isDark) Color(0xFFBB86FC) else Color(0xFF7C4DFF),
                                radius = 1.dp.toPx(),
                                center = Offset(x.toFloat(), y.toFloat())
                            )
                        }
                    }
                }
        )

        // نمایش لکه‌های رنگی (Blobs) در پس‌زمینه
        Blob(
            color = PurpleAccent.copy(alpha = 0.3f),
            size = 240.dp,
            modifier = Modifier.align(Alignment.TopEnd).offset(x = 60.dp, y = (-60).dp)
        )
        
        Blob(
            color = OrangeAccent.copy(alpha = 0.2f),
            size = 200.dp,
            modifier = Modifier.align(Alignment.BottomStart).offset(x = (-50).dp, y = 80.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // بخش آیکون و انیمیشن ترول
            Box(contentAlignment = Alignment.Center) {
                // دایره انیمیشنی پشت ترول
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .graphicsLayer {
                            scaleX = pulseScale
                            scaleY = pulseScale
                            alpha = pulseAlpha
                        }
                        .background(PurpleAccent.copy(alpha = 0.5f), CircleShape)
                )
                
                // کاراکتر ترول با افکت شناور
                Box(
                    modifier = Modifier
                        .offset(y = floatOffset.dp)
                        .size(140.dp)
                        .background(
                            if (isDark) Color.White.copy(alpha = 0.2f) else Color.White.copy(alpha = 0.1f),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    TrollMascot(size = 110.dp)
                }
            }

            // نام و نشان برنامه
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = strings.appName,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Black,
                    color = textColor
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Brush.horizontalGradient(listOf(PurpleAccent, OrangeAccent)))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "${strings.puzzleBadge} ${AppConstants.ICON_DEFAULT_PUZZLE}",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // شعار برنامه
            Text(
                text = strings.tagline,
                color = textColor.copy(alpha = 0.7f),
                fontSize = 16.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.padding(horizontal = 40.dp)
            )

            // نوار لودینگ و متن وضعیت
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 32.dp)
            ) {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .width(180.dp)
                        .height(4.dp)
                        .clip(CircleShape),
                    color = PurpleAccent,
                    trackColor = textColor.copy(alpha = 0.1f),
                )
                Text(
                    text = strings.loading,
                    color = textColor.copy(alpha = 0.4f),
                    fontSize = 12.sp
                )
            }
        }
    }
}

// ایجاد یک لکه رنگی محو شده در پس‌زمینه
@Composable
fun Blob(color: Color, size: Dp, modifier: Modifier) {
    Box(
        modifier = modifier
            .size(size)
            .background(color, CircleShape)
            .blur(60.dp)
    )
}
