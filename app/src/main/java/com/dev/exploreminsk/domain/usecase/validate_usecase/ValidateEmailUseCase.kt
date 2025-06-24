package com.dev.exploreminsk.domain.usecase.validate_usecase

import android.util.Patterns
import com.dev.exploreminsk.R
import com.dev.exploreminsk.domain.model.ValidationResult
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {

    operator fun invoke(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                result = false,
                stringRes = R.string.email_incorrect_error_label
            )
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                result = false,
                stringRes = R.string.email_incorrect_error_label
            )
        }

        return ValidationResult(
            result = true
        )
    }
}