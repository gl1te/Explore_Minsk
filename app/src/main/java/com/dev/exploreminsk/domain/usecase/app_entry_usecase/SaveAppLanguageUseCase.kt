package com.dev.exploreminsk.domain.usecase.app_entry_usecase

import com.dev.exploreminsk.domain.manager.LocalUserManager
import com.dev.exploreminsk.presentation.settings.components.AppLanguage
import javax.inject.Inject

class SaveAppLanguageUseCase @Inject constructor(
    private val localUserManager: LocalUserManager
) {

    suspend operator fun invoke(language: AppLanguage) {
        localUserManager.saveAppLanguage(language)
    }

}