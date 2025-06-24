package com.dev.exploreminsk.presentation.edit_profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.exploreminsk.R
import com.dev.exploreminsk.domain.model.UserUpdateRequest
import com.dev.exploreminsk.domain.usecase.user_usecases.UserUseCases
import com.dev.exploreminsk.domain.usecase.validate_usecase.ValidateUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val validateUseCases: ValidateUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(EditProfileScreenState())
    val state: StateFlow<EditProfileScreenState> = _state

    init {
        getUserInfo()
    }

    fun onEvent(event: EditProfileEvent) {
        when (event) {
            is EditProfileEvent.OnChangeEmail -> {
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
            }

            is EditProfileEvent.OnChangeUsername -> {
                _state.update {
                    it.copy(
                        login = event.username
                    )
                }
            }

            EditProfileEvent.OnSubmit -> {
                validateAndRegister()
            }

            EditProfileEvent.OnDismissDialog -> {
                _state.update {
                    it.copy(
                        showEmailConfirmationDialog = false
                    )
                }
            }

            is EditProfileEvent.OnChangeImage -> {
                _state.update {
                    it.copy(
                        byteArray = event.image,
                        selectedImageUri = event.uri
                    )
                }
            }
        }
    }

    private fun validateAndRegister() {
        val emailError = validateUseCases.validateEmailUseCase(_state.value.email)
        val loginError = validateUseCases.validateLoginUseCase(_state.value.login)

        val hasError = listOf(
            emailError,
            loginError
        ).any {
            !it.result
        }

        if (hasError) {
            _state.update {
                it.copy(
                    emailError = emailError.stringRes,
                    loginError = loginError.stringRes
                )
            }
            return
        }

        updateUser()
    }

    private fun updateUser() {
        viewModelScope.launch {
            val updatedUser = UserUpdateRequest(
                email = state.value.email,
                username = state.value.login,
                byteArray = state.value.byteArray
            )
            userUseCases.updateUserUseCase(updatedUser)
                .onSuccess {
                    val emailChanged = state.value.email != state.value.originalEmail
                    _state.update {
                        it.copy(
                            result = true,
                            showEmailConfirmationDialog = emailChanged
                        )
                    }
                }
                .onFailure { error ->
                    val isRateLimit = error.message?.contains(
                        "over_email_send_rate_limit",
                        ignoreCase = true
                    ) == true

                    _state.update {
                        it.copy(
                            errorMessage = if (isRateLimit) R.string.confirm_timeout_error_label else R.string.profile_update_error_label,
                            result = false
                        )
                    }
                    Log.e("ErrorSaving", error.message.toString())
                }
        }
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            userUseCases.getUserUseCases()
                .onSuccess { user ->
                    _state.update {
                        it.copy(
                            email = user.email,
                            originalEmail = user.email,
                            login = user.username ?: "",
                            imageUrl = user.imageUrl,
                            isLoading = false
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = R.string.profile_get_info_error_label
                        )
                    }
                }
        }
    }
}