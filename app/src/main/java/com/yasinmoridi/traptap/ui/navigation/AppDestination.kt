package com.yasinmoridi.traptap.ui.navigation

@kotlinx.serialization.Serializable
sealed interface AppDestination {

    @kotlinx.serialization.Serializable
    data object Splash : AppDestination

    @kotlinx.serialization.Serializable
    data object Levels : AppDestination

    @kotlinx.serialization.Serializable
    data object Game : AppDestination

    @kotlinx.serialization.Serializable
    data object Settings : AppDestination
}
