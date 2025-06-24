package com.dev.exploreminsk.domain.usecase.app_entry_usecase

import com.dev.exploreminsk.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadUserAuthUseCase @Inject constructor(
    private val localUserManager: LocalUserManager
) {

    operator fun invoke(): Flow<String> {
        return localUserManager.readUserAuth()
    }

}