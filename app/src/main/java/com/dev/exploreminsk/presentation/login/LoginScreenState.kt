package com.dev.exploreminsk.presentation.login

import androidx.annotation.StringRes

data class LoginScreenState(
    var email: String = "",
    var password: String = "",
    @StringRes val passwordError: Int? = null,
    @StringRes val emailError: Int? = null,
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false,
    @StringRes val loginError: Int? = null
)
