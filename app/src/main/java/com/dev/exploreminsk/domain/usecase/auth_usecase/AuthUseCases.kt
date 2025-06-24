package com.dev.exploreminsk.domain.usecase.auth_usecase

data class AuthUseCases(
    val loginUserUseCase: LoginUserUseCase,
    val registerUserUseCase: RegisterUserUseCase,
    val isLoggedInUseCase: IsLoggedInUseCase
)
