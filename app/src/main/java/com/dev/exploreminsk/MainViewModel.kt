package com.dev.exploreminsk

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.exploreminsk.domain.manager.LocalUserManager
import com.dev.exploreminsk.domain.usecase.app_entry_usecase.UserUseCases
import com.dev.exploreminsk.domain.usecase.auth_usecase.AuthUseCases
import com.dev.exploreminsk.presentation.navgraph.Route
import com.dev.exploreminsk.presentation.settings.components.AppLanguage
import com.dev.exploreminsk.presentation.settings.components.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val localUserManager: LocalUserManager,
    private val userAuthUseCases: UserUseCases,
    private val authUseCases: AuthUseCases
) : ViewModel() {

    var splashCondition by mutableStateOf(true)
        private set

    var startDestination by mutableStateOf(Route.AppStartNavigation.route)
        private set

    private val _appTheme = MutableStateFlow(AppTheme.SYSTEM)
    val appTheme: StateFlow<AppTheme> = _appTheme

    private val _appLanguage = MutableStateFlow(AppLanguage.en)
    val appLanguage: StateFlow<AppLanguage> = _appLanguage

    init {
        viewModelScope.launch {
            localUserManager.readAppEntry().first().let { isComplete ->
                if (!isComplete) {
                    startDestination = Route.AppStartNavigation.route
                } else {
                    val token = userAuthUseCases.readUserAuthUseCase().first()
                    val result = authUseCases.isLoggedInUseCase(token).getOrNull()

                    startDestination = if (result?.isSuccess == true) {
                        Route.AppNavigation.route
                    } else {
                        Route.AuthNavigation.route
                    }
                }
            }
            localUserManager.readAppTheme().onEach { theme ->
                _appTheme.value = when (theme) {
                    "DARK" -> AppTheme.DARK
                    "LIGHT" -> AppTheme.LIGHT
                    else -> AppTheme.SYSTEM
                }
            }.launchIn(viewModelScope)

            localUserManager.readAppLanguage().onEach { language ->
                _appLanguage.value = when (language) {
                    "en" -> AppLanguage.en
                    "ru" -> AppLanguage.ru
                    else -> AppLanguage.en
                }
            }.launchIn(viewModelScope)
            delay(300)
            splashCondition = false
        }
    }

}