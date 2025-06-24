package com.dev.exploreminsk.domain.usecase.user_usecases

import com.dev.exploreminsk.domain.model.User
import com.dev.exploreminsk.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): Result<User> {
        return userRepository.getUser()
    }

}