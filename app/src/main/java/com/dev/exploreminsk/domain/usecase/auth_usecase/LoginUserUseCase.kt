package com.dev.exploreminsk.domain.usecase.auth_usecase

import com.dev.exploreminsk.domain.model.AuthResult
import com.dev.exploreminsk.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(email: String, password: String): Result<AuthResult> {
        return authRepository.login(email, password)
    }


}