package com.dev.exploreminsk.domain.usecase.auth_usecase

import com.dev.exploreminsk.domain.model.AuthResult
import com.dev.exploreminsk.domain.repository.AuthRepository
import javax.inject.Inject

class IsLoggedInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(token: String): Result<AuthResult> {
        return authRepository.isLoggedIn(token)
    }

}