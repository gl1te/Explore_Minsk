package com.dev.exploreminsk.domain.model

import androidx.annotation.StringRes
import io.ktor.client.statement.HttpResponse

data class AuthResult(
    val isSuccess: Boolean,
    val message: String? = null,
    val token: String? = null,
    val restResponse: HttpResponse? = null,
    val error: Throwable? = null
)
