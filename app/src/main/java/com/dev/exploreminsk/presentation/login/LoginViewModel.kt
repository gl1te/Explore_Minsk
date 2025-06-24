package com.dev.exploreminsk.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.exploreminsk.R
import com.dev.exploreminsk.domain.usecase.app_entry_usecase.SaveUserAuthUseCase
import com.dev.exploreminsk.domain.usecase.auth_usecase.AuthUseCases
import com.dev.exploreminsk.domain.usecase.validate_usecase.ValidateUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateUseCases: ValidateUseCases,
    private val authUseCases: AuthUseCases,
    private val saveUserAuthUseCase: SaveUserAuthUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginScreenState())
    val state: StateFlow<LoginScreenState> = _state

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChange -> {
                _state.update {
                    it.copy(
                        email = event.email,
                        emailError = null,
                        loginError = null
                    )
                }
            }

            is LoginEvent.OnPasswordChange -> {
                _state.update {
                    it.copy(
                        password = event.password,
                        passwordError = null,
                        loginError = null
                    )
                }
            }

            LoginEvent.OnSubmit -> {
                val passwordError = validateUseCases.validatePasswordUseCase(_state.value.password)
                val emailError = validateUseCases.validateEmailUseCase(_state.value.email)

                val hasError = listOf(
                    passwordError,
                    emailError
                ).any {
                    !it.result
                }

                if (hasError) {
                    _state.update {
                        it.copy(
                            passwordError = passwordError.stringRes,
                            emailError = emailError.stringRes,
                        )
                    }
                    return
                }

                login()
            }
        }
    }

    private fun login() {
        val email = _state.value.email
        val password = _state.value.password

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, loginError = null) }
            delay(1000)
            authUseCases.loginUserUseCase(email, password)
                .onSuccess {
                    it.token?.let {
                        saveUserAuth(it)
                    }
                    _state.update { it.copy(isLoading = false, loginSuccess = true) }
                }
                .onFailure {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            loginError = R.string.invalid_credentials
                        )
                    }
                }
        }
    }

    private fun saveUserAuth(token: String) {
        viewModelScope.launch {
            saveUserAuthUseCase(token)
        }
    }

}