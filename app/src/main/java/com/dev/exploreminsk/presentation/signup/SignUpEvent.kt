package com.dev.exploreminsk.presentation.signup

sealed class SignUpEvent {
    data class OnEmailChange(val email: String) : SignUpEvent()
    data class OnPasswordChange(val password: String) : SignUpEvent()
    object OnSubmit : SignUpEvent()
}
