package com.dev.exploreminsk.domain.usecase.app_entry_usecase

import com.dev.exploreminsk.domain.manager.LocalUserManager
import javax.inject.Inject

class SaveAppEntryUser @Inject constructor(
    private val localUserManager: LocalUserManager
) {

    suspend operator fun invoke(){
        localUserManager.saveAppEntry()
    }

}