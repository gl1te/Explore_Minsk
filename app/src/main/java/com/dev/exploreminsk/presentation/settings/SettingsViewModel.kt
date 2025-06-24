package com.dev.exploreminsk.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.exploreminsk.domain.usecase.app_entry_usecase.AppEntryUseCases
import com.dev.exploreminsk.presentation.settings.components.AppLanguage
import com.dev.exploreminsk.presentation.settings.components.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsScreenState())
    val state = _state

    init {
        appEntryUseCases.readAppThemeUseCase().onEach { theme ->
            val appTheme = when (theme) {
                "DARK" -> AppTheme.DARK
                "LIGHT" -> AppTheme.LIGHT
                else -> AppTheme.SYSTEM
            }
            _state.update {
                it.copy(
                    appTheme = appTheme
                )
            }
        }.launchIn(viewModelScope)

        appEntryUseCases.readAppLanguageUseCase().onEach { language ->
            val appLanguage = when (language) {
                "en" -> AppLanguage.en
                "ru" -> AppLanguage.ru
                else -> AppLanguage.en
            }
            _state.update {
                it.copy(
                    appLanguage = appLanguage
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onThemeChanged(theme: AppTheme) {
        _state.update {
            it.copy(
                appTheme = theme
            )
        }
        viewModelScope.launch {
            appEntryUseCases.saveAppThemeUseCase(theme)
        }
    }

    fun onLanguageChanged(language: AppLanguage) {
        _state.update {
            it.copy(
                appLanguage = language
            )
        }
        viewModelScope.launch {
            appEntryUseCases.saveAppLanguageUseCase(language)
        }
    }
}