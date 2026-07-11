package com.yasinmoridi.traptap.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yasinmoridi.traptap.ui.theme.AppColors
import com.yasinmoridi.traptap.ui.util.AppStrings
import com.yasinmoridi.traptap.util.AppConstants

data class ShopItem(val id: Int, val coins: Int, val price: String, val icon: String)

@Composable
fun ShopScreen(
    strings: AppStrings,
    isDark: Boolean,
    onPurchase: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val textPrimary = if (isDark) AppColors.Dark.TextPrimary else AppColors.Light.TextPrimary
    val textSecondary = if (isDark) AppColors.Dark.TextSecondary else AppColors.Light.TextSecondary
    val surfaceColor = if (isDark) AppColors.Dark.Surface else AppColors.Light.Surface
    val cardSurface = if (isDark) AppColors.Dark.Card else AppColors.Light.Card

    val shopItems = listOf(
        ShopItem(1, 100, "$0.99", "💰"),
        ShopItem(2, 500, "$4.99", "💎"),
        ShopItem(3, 1200, "$9.99", "🎁"),
        ShopItem(4, 2500, "$19.99", "🏺"),
        ShopItem(5, 6000, "$39.99", "👑"),
        ShopItem(6, 15000, "$79.99", "🪐")
    )

    var selectedItem by remember { mutableStateOf<ShopItem?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(surfaceColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = strings.shopTitle,
            fontSize = 28.sp,
            fontWeight = FontWeight.Black,
            color = textPrimary
        )
        Text(
            text = strings.shopSubtitle,
            fontSize = 14.sp,
            color = textSecondary,
            modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(shopItems) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .clickable { selectedItem = item }
                        .border(
                            width = 1.dp,
                            color = AppColors.PurpleAccent.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(24.dp)
                        ),
                    colors = CardDefaults.cardColors(containerColor = cardSurface)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(item.icon, fontSize = 40.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "${item.coins} ${AppConstants.ICON_COIN}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = textPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.price,
                            fontSize = 14.sp,
                            color = AppColors.PurpleAccent,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }
        }
    }

    if (selectedItem != null) {
        AlertDialog(
            onDismissRequest = { selectedItem = null },
            title = { 
                Text(
                    strings.freeCoinsTitle, 
                    fontWeight = FontWeight.Black, 
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                ) 
            },
            text = { 
                Text(
                    strings.freeCoinsMsg, 
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                ) 
            },
            confirmButton = {
                Button(
                    onClick = {
                        onPurchase(selectedItem!!.coins)
                        selectedItem = null
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.PurpleAccent)
                ) {
                    Text("❤️ Thanks!", fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}
