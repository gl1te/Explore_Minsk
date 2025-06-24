package com.dev.exploreminsk.presentation.signup

import android.util.Log
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
class SignUpViewModel @Inject constructor(
    private val validateUseCases: ValidateUseCases,
    private val authUseCases: AuthUseCases,
    private val saveUserAuthUseCase: SaveUserAuthUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpScreenState())
    val state: StateFlow<SignUpScreenState> = _state

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.OnEmailChange -> {
                _state.update {
                    it.copy(
                        email = event.email,
                        emailError = null,
                        errorMessage = null
                    )
                }
            }

            is SignUpEvent.OnPasswordChange -> {
                _state.update {
                    it.copy(
                        password = event.password,
                        passwordError = null,
                        errorMessage = null
                    )
                }
            }

            SignUpEvent.OnSubmit -> {
                _state.update {
                    it.copy(
                        passwordError = null,
                        emailError = null,
                        errorMessage = null
                    )
                }
                validateAndRegister()
            }
        }
    }

    private fun validateAndRegister() {
        val emailError = validateUseCases.validateEmailUseCase(_state.value.email)
        val passwordError = validateUseCases.validatePasswordUseCase(_state.value.password)

        val hasError = listOf(
            emailError,
            passwordError
        ).any {
            !it.result
        }

        if (hasError) {
            _state.update {
                it.copy(
                    emailError = emailError.stringRes,
                    passwordError = passwordError.stringRes
                )
            }
            return
        }

        register()
    }

    private fun register() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            delay(1000)

            val email = _state.value.email
            val password = _state.value.password

            authUseCases.registerUserUseCase(email, password)
                .onSuccess { result ->
                    if (result.isSuccess) {
                        _state.update {
                            it.copy(
                                registerSuccess = true,
                                isLoading = false
                            )
                        }
                    } else {
                        val code = result.restResponse?.status?.value
                        when (code) {
                            400 -> _state.update {
                                it.copy(
                                    registerSuccess = false,
                                    errorMessage = R.string.invalid_credentials,
                                    isLoading = false
                                )
                            }

                            409 -> _state.update {
                                it.copy(
                                    registerSuccess = false,
                                    errorMessage = R.string.user_conflict,
                                    isLoading = false
                                )
                            }

                            422 -> _state.update {
                                it.copy(
                                    registerSuccess = false,
                                    errorMessage = R.string.user_conflict,
                                    isLoading = false
                                )
                            }

                            else -> _state.update {
                                it.copy(
                                    registerSuccess = false,
                                    errorMessage = R.string.unknown_error,
                                    isLoading = false
                                )
                            }
                        }
                    }
                }
        }


    }
}