package com.dev.exploreminsk.domain.usecase.validate_usecase

import com.dev.exploreminsk.R
import com.dev.exploreminsk.domain.model.ValidationResult
import javax.inject.Inject

class ValidateLoginUseCase @Inject constructor() {

    operator fun invoke(login: String): ValidationResult {
        if (login.isBlank()) {
            return ValidationResult(
                result = false,
                R.string.login_length_error_label
            )

        }

        return ValidationResult(
            result = true
        )
    }
}