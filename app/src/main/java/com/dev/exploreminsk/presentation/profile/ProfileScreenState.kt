package com.dev.exploreminsk.presentation.profile

import com.dev.exploreminsk.domain.model.User

data class ProfileScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val user: User? = null
)
