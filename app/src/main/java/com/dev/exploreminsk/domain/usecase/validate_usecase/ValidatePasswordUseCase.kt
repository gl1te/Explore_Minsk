package com.dev.exploreminsk.domain.usecase.validate_usecase

import com.dev.exploreminsk.R
import com.dev.exploreminsk.domain.model.ValidationResult
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {

    operator fun invoke(password: String): ValidationResult {
        if (password.length < 6) {
            return ValidationResult(
                result = false,
                R.string.password_length_error_label
            )

        }
        val containsLettersAndDigits =
            password.any { it.isDigit() } && password.any { it.isLetter() }
        if (! containsLettersAndDigits) {
            return ValidationResult(
                result = false,
                R.string.password_contains_error_label
            )
        }

        return ValidationResult(
            result = true
        )
    }

}