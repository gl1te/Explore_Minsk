package com.dev.exploreminsk.domain.usecase.user_usecases

import com.dev.exploreminsk.domain.model.UserUpdateRequest
import com.dev.exploreminsk.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(updateRequest: UserUpdateRequest): Result<Boolean> {
        return userRepository.updateUser(updateRequest)
    }

}