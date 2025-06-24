package com.dev.exploreminsk.domain.usecase.auth_usecase

import com.dev.exploreminsk.domain.model.AuthResult
import com.dev.exploreminsk.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(email: String, password: String) =
        authRepository.register(email, password)

}