package com.dev.exploreminsk.domain.usecase.app_entry_usecase

import com.dev.exploreminsk.domain.manager.LocalUserManager
import com.dev.exploreminsk.presentation.settings.components.AppTheme
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveAppThemeUseCase @Inject constructor(
    private val localUserManager: LocalUserManager
) {

    suspend operator fun invoke(appTheme: AppTheme){
        return localUserManager.saveAppTheme(appTheme)
    }

}