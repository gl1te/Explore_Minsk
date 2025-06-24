package com.dev.exploreminsk.presentation.settings

import com.dev.exploreminsk.presentation.settings.components.AppLanguage
import com.dev.exploreminsk.presentation.settings.components.AppTheme

data class SettingsScreenState(
    var appTheme: AppTheme = AppTheme.SYSTEM,
    val appLanguage: AppLanguage = AppLanguage.en
)
