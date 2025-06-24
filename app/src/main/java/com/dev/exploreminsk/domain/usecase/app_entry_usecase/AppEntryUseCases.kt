package com.dev.exploreminsk.domain.usecase.app_entry_usecase

data class AppEntryUseCases(
    val readAppEntryUseCase: ReadAppEntryUseCase,
    val saveAppEntryUser: SaveAppEntryUser,
    val readAppThemeUseCase: ReadAppThemeUseCase,
    val saveAppThemeUseCase: SaveAppThemeUseCase,
    val saveAppLanguageUseCase: SaveAppLanguageUseCase,
    val readAppLanguageUseCase: ReadAppLanguageUseCase
)
