package com.yasinmoridi.traptap.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppDestination {

    // صفحه خوش‌آمدگویی و لودینگ اولیه
    @Serializable
    data object Splash : AppDestination

    // صفحه انتخاب مراحل بازی
    @Serializable
    data object Levels : AppDestination

    // صفحه اصلی بازی و چالش‌ها
    @Serializable
    data object Game : AppDestination

    // صفحه تنظیمات اپلیکیشن
    @Serializable
    data object Settings : AppDestination

    // فروشگاه سکه
    @Serializable
    data object Shop : AppDestination
}
