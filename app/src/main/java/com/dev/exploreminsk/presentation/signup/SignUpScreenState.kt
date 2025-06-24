package com.dev.exploreminsk.presentation.signup

import androidx.annotation.StringRes

data class SignUpScreenState(
    var email: String = "",
    var password: String = "",
    val isLoading: Boolean = false,
    @StringRes val passwordError: Int? = null,
    @StringRes val emailError: Int? = null,
    val registerSuccess: Boolean = false,
    @StringRes val errorMessage: Int? = null
)
