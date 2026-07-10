package com.yasinmoridi.traptap.data.local

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.yasinmoridi.traptap.util.AppConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = AppConstants.DATASTORE_NAME)

class PreferenceManager(private val context: Context) {

    private object Keys {
        val COINS = intPreferencesKey("user_coins")
        val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
        val LANGUAGE = stringPreferencesKey("app_language")
        val LEVEL_2_TRAP = booleanPreferencesKey("level_2_trap_active")
    }

    // دریافت اطلاعات به صورت زنده
    val coins: Flow<Int> = context.dataStore.data.map { it[Keys.COINS] ?: AppConstants.INITIAL_COINS }
    val isDarkMode: Flow<Boolean> = context.dataStore.data.map { it[Keys.IS_DARK_MODE] ?: false }
    val language: Flow<String> = context.dataStore.data.map { it[Keys.LANGUAGE] ?: AppConstants.LANG_EN }
    val isLevel2TrapActive: Flow<Boolean> = context.dataStore.data.map { it[Keys.LEVEL_2_TRAP] ?: false }

    // توابع تغییر اطلاعات
    suspend fun updateCoins(newAmount: Int) {
        context.dataStore.edit { it[Keys.COINS] = newAmount }
    }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { it[Keys.IS_DARK_MODE] = enabled }
    }

    suspend fun setLanguage(lang: String) {
        context.dataStore.edit { it[Keys.LANGUAGE] = lang }
    }

    suspend fun setLevel2Trap(active: Boolean) {
        context.dataStore.edit { it[Keys.LEVEL_2_TRAP] = active }
    }
}
