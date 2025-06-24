package com.dev.exploreminsk.domain.model

import androidx.annotation.StringRes

data class ValidationResult(
    val result: Boolean,
    @StringRes val stringRes: Int? = null
)