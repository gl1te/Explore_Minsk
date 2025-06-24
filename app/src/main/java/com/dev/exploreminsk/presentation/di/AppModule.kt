package com.dev.exploreminsk.presentation.di

import android.content.Context
import androidx.room.Room
import com.dev.exploreminsk.BuildConfig
import com.dev.exploreminsk.data.database.ExploreDao
import com.dev.exploreminsk.data.database.ExploreDatabase
import com.dev.exploreminsk.data.manager.LocalUserManagerImpl
import com.dev.exploreminsk.data.repository.AuthRepositoryImpl
import com.dev.exploreminsk.data.repository.CategoryRepositoryImpl
import com.dev.exploreminsk.data.repository.PlacesRepositoryImpl
import com.dev.exploreminsk.data.repository.RoomPlaceRepositoryImpl
import com.dev.exploreminsk.data.repository.StorageRepositoryImpl
import com.dev.exploreminsk.data.repository.UserRepositoryImpl
import com.dev.exploreminsk.data.util.Constants.ROOM_DATABASE_NAME
import com.dev.exploreminsk.domain.manager.LocalUserManager
import com.dev.exploreminsk.domain.repository.AuthRepository
import com.dev.exploreminsk.domain.repository.CategoryRepository
import com.dev.exploreminsk.domain.repository.PlacesRepository
import com.dev.exploreminsk.domain.repository.RoomPlaceRepository
import com.dev.exploreminsk.domain.repository.StorageRepository
import com.dev.exploreminsk.domain.repository.UserRepository
import com.dev.exploreminsk.domain.usecase.app_entry_usecase.AppEntryUseCases
import com.dev.exploreminsk.domain.usecase.app_entry_usecase.ReadAppEntryUseCase
import com.dev.exploreminsk.domain.usecase.app_entry_usecase.ReadAppLanguageUseCase
import com.dev.exploreminsk.domain.usecase.app_entry_usecase.ReadAppThemeUseCase
import com.dev.exploreminsk.domain.usecase.app_entry_usecase.ReadUserAuthUseCase
import com.dev.exploreminsk.domain.usecase.app_entry_usecase.SaveAppEntryUser
import com.dev.exploreminsk.domain.usecase.app_entry_usecase.SaveAppLanguageUseCase
import com.dev.exploreminsk.domain.usecase.app_entry_usecase.SaveAppThemeUseCase
import com.dev.exploreminsk.domain.usecase.app_entry_usecase.SaveUserAuthUseCase
import com.dev.exploreminsk.domain.usecase.app_entry_usecase.UserUseCases
import com.dev.exploreminsk.domain.usecase.auth_usecase.AuthUseCases
import com.dev.exploreminsk.domain.usecase.auth_usecase.IsLoggedInUseCase
import com.dev.exploreminsk.domain.usecase.auth_usecase.LoginUserUseCase
import com.dev.exploreminsk.domain.usecase.auth_usecase.RegisterUserUseCase
import com.dev.exploreminsk.domain.usecase.category_usecase.CategoryUseCases
import com.dev.exploreminsk.domain.usecase.category_usecase.GetAllCategoriesUseCase
import com.dev.exploreminsk.domain.usecase.place_usecase.GetAllPlacesByCategory
import com.dev.exploreminsk.domain.usecase.place_usecase.GetAllPlacesUseCase
import com.dev.exploreminsk.domain.usecase.place_usecase.GetPlacesByNameAndCategory
import com.dev.exploreminsk.domain.usecase.place_usecase.PlaceUseCases
import com.dev.exploreminsk.domain.usecase.room_usecase.DeletePlaceUseCase
import com.dev.exploreminsk.domain.usecase.room_usecase.GetPlacesUseCase
import com.dev.exploreminsk.domain.usecase.room_usecase.RoomPlaceUseCases
import com.dev.exploreminsk.domain.usecase.room_usecase.UpsertPlaceUseCase
import com.dev.exploreminsk.domain.usecase.user_usecases.GetUserUseCase
import com.dev.exploreminsk.domain.usecase.user_usecases.UpdateUserUseCase
import com.dev.exploreminsk.domain.usecase.validate_usecase.ValidateEmailUseCase
import com.dev.exploreminsk.domain.usecase.validate_usecase.ValidateLoginUseCase
import com.dev.exploreminsk.domain.usecase.validate_usecase.ValidatePasswordUseCase
import com.dev.exploreminsk.domain.usecase.validate_usecase.ValidateUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = BuildConfig.supabaseUrl,
            supabaseKey = BuildConfig.supabaseSecret,
        ) {
            install(Auth)
            install(Storage)
            install(Postgrest)
        }
    }

    @Provides
    @Singleton
    fun provideRoomPlaceRepository(exploreDao: ExploreDao): RoomPlaceRepository {
        return RoomPlaceRepositoryImpl(exploreDao)
    }

    @Provides
    @Singleton
    fun provideExploreDao(db: ExploreDatabase): ExploreDao {
        return db.exploreDao
    }

    @Provides
    @Singleton
    fun provideExploreDatabase(
        @ApplicationContext context: Context,
    ): ExploreDatabase {
        return Room.databaseBuilder(
            context = context.applicationContext,
            klass = ExploreDatabase::class.java,
            name = ROOM_DATABASE_NAME
        ).fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        supabaseClient: SupabaseClient,
    ): AuthRepository {
        return AuthRepositoryImpl(supabaseClient)
    }

    @Provides
    @Singleton
    fun provideStorageRepository(
        supabaseClient: SupabaseClient,
    ): StorageRepository {
        return StorageRepositoryImpl(supabaseClient)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        supabaseClient: SupabaseClient,
        storageRepository: StorageRepository,
    ): UserRepository {
        return UserRepositoryImpl(supabaseClient, storageRepository)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(
        supabaseClient: SupabaseClient,
    ): CategoryRepository {
        return CategoryRepositoryImpl(supabaseClient)
    }

    @Provides
    @Singleton
    fun providePlacesRepository(
        supabaseClient: SupabaseClient,
        categoryRepository: CategoryRepository
    ): PlacesRepository {
        return PlacesRepositoryImpl(supabaseClient,categoryRepository)
    }

    @Provides
    @Singleton
    fun provideSupabaseDatabase(supabaseClient: SupabaseClient): Postgrest {
        return supabaseClient.postgrest
    }

    @Provides
    @Singleton
    fun provideSupabaseStorage(supabaseClient: SupabaseClient): Storage {
        return supabaseClient.storage
    }

    @Provides
    @Singleton
    fun provideSupabaseAuth(supabaseClient: SupabaseClient): Auth {
        return supabaseClient.auth
    }

    @Provides
    @Singleton
    fun provideLocalUserManager(@ApplicationContext context: Context): LocalUserManager {
        return LocalUserManagerImpl(context)
    }

    @Provides
    @Singleton
    fun provideAppEntryUseCases(localUserManager: LocalUserManager): AppEntryUseCases {
        return AppEntryUseCases(
            readAppEntryUseCase = ReadAppEntryUseCase(localUserManager),
            saveAppEntryUser = SaveAppEntryUser(localUserManager),
            readAppThemeUseCase = ReadAppThemeUseCase(localUserManager),
            saveAppThemeUseCase = SaveAppThemeUseCase(localUserManager),
            saveAppLanguageUseCase = SaveAppLanguageUseCase(localUserManager),
            readAppLanguageUseCase = ReadAppLanguageUseCase(localUserManager),
        )
    }

    @Provides
    @Singleton
    fun provideAuthUseCases(authRepository: AuthRepository): AuthUseCases {
        return AuthUseCases(
            registerUserUseCase = RegisterUserUseCase(authRepository),
            loginUserUseCase = LoginUserUseCase(authRepository),
            isLoggedInUseCase = IsLoggedInUseCase(authRepository)
        )
    }

    @Provides
    @Singleton
    fun provideUserAuthUseCases(localUserManager: LocalUserManager): UserUseCases {
        return UserUseCases(
            saveUserAuthUseCases = SaveUserAuthUseCase(localUserManager),
            readUserAuthUseCase = ReadUserAuthUseCase(localUserManager)
        )
    }

    @Provides
    @Singleton
    fun provideValidateUseCases(): ValidateUseCases {
        return ValidateUseCases(
            validateEmailUseCase = ValidateEmailUseCase(),
            validatePasswordUseCase = ValidatePasswordUseCase(),
            validateLoginUseCase = ValidateLoginUseCase()
        )
    }

    @Provides
    @Singleton
    fun provideCategoryUseCases(
        categoryRepository: CategoryRepository,
    ): CategoryUseCases {
        return CategoryUseCases(
            getAllCategoriesUseCase = GetAllCategoriesUseCase(categoryRepository)
        )
    }

    @Provides
    @Singleton
    fun provideUserUseCases(
        userRepository: UserRepository,
    ): com.dev.exploreminsk.domain.usecase.user_usecases.UserUseCases {
        return com.dev.exploreminsk.domain.usecase.user_usecases.UserUseCases(
            getUserUseCases = GetUserUseCase(userRepository),
            updateUserUseCase = UpdateUserUseCase(userRepository)
        )
    }

    @Provides
    @Singleton
    fun providePlaceUseCases(
        placeRepository: PlacesRepository,
    ): PlaceUseCases {
        return PlaceUseCases(
            getAllPlacesUseCase = GetAllPlacesUseCase(placeRepository),
            getAllPlacesByCategory = GetAllPlacesByCategory(placeRepository),
            getPlacesByNameAndCategory = GetPlacesByNameAndCategory(placeRepository)
        )
    }

    @Provides
    @Singleton
    fun provideRoomPlaceUseCase(roomPlaceRepository: RoomPlaceRepository): RoomPlaceUseCases {
        return RoomPlaceUseCases(
            deletePlaceUseCase = DeletePlaceUseCase(roomPlaceRepository),
            upsertPlaceUseCase = UpsertPlaceUseCase(roomPlaceRepository),
            getPlacesUseCase = GetPlacesUseCase(roomPlaceRepository)
        )
    }

}