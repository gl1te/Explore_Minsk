package com.dev.exploreminsk.domain.manager

import com.dev.exploreminsk.presentation.settings.components.AppLanguage
import com.dev.exploreminsk.presentation.settings.components.AppTheme
import kotlinx.coroutines.flow.Flow

interface LocalUserManager {

    fun readAppEntry(): Flow<Boolean>
    suspend fun saveAppEntry()

    fun readUserAuth(): Flow<String>
    suspend fun saveUserAuth(token: String)

    fun readAppTheme(): Flow<String>
    suspend fun saveAppTheme(appTheme: AppTheme)

    fun readAppLanguage(): Flow<String>
    suspend fun saveAppLanguage(language: AppLanguage)

}
