package com.dev.exploreminsk.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dev.exploreminsk.data.manager.PreferencesKeys.APP_ENTRY_KEY
import com.dev.exploreminsk.data.manager.PreferencesKeys.APP_LANGUAGE_KEY
import com.dev.exploreminsk.data.manager.PreferencesKeys.APP_THEME_KEY
import com.dev.exploreminsk.data.manager.PreferencesKeys.USER_ENTRY_KEY
import com.dev.exploreminsk.data.util.Constants.APP_ENTRY
import com.dev.exploreminsk.data.util.Constants.APP_LANGUAGE
import com.dev.exploreminsk.data.util.Constants.APP_THEME
import com.dev.exploreminsk.data.util.Constants.USER_APP_ENTRY
import com.dev.exploreminsk.data.util.Constants.USER_SETTINGS
import com.dev.exploreminsk.domain.manager.LocalUserManager
import com.dev.exploreminsk.presentation.settings.components.AppLanguage
import com.dev.exploreminsk.presentation.settings.components.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class LocalUserManagerImpl(
    private val context: Context,
) : LocalUserManager {

    override fun readAppEntry(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[APP_ENTRY_KEY] ?: false
        }
    }

    override suspend fun saveAppEntry() {
        context.dataStore.edit { settings ->
            settings[APP_ENTRY_KEY] = true
        }
    }

    override fun readUserAuth(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_ENTRY_KEY] ?: ""
        }
    }

    override suspend fun saveUserAuth(token: String) {
        context.dataStore.edit { settings ->
            settings[USER_ENTRY_KEY] = token
        }
    }

    override fun readAppTheme(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[APP_THEME_KEY] ?: AppTheme.SYSTEM.name
        }
    }

    override suspend fun saveAppTheme(appTheme: AppTheme) {
        context.dataStore.edit { settings ->
            settings[APP_THEME_KEY] = appTheme.name
        }
    }

    override fun readAppLanguage(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[APP_LANGUAGE_KEY] ?: "en"
        }
    }

    override suspend fun saveAppLanguage(language: AppLanguage) {
        context.dataStore.edit { settings ->
            settings[APP_LANGUAGE_KEY] = language.name
        }
    }

}

private val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(
    name = USER_SETTINGS
)

private object PreferencesKeys {
    val APP_ENTRY_KEY = booleanPreferencesKey(name = APP_ENTRY)
    val USER_ENTRY_KEY = stringPreferencesKey(name = USER_APP_ENTRY)
    val APP_THEME_KEY = stringPreferencesKey(name = APP_THEME)
    val APP_LANGUAGE_KEY = stringPreferencesKey(name = APP_LANGUAGE)
}