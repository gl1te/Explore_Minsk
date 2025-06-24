package com.dev.exploreminsk.domain.usecase.validate_usecase

data class ValidateUseCases(
    val validateEmailUseCase: ValidateEmailUseCase,
    val validatePasswordUseCase: ValidatePasswordUseCase,
    val validateLoginUseCase: ValidateLoginUseCase
)
