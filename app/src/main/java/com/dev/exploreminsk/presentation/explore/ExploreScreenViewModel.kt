package com.dev.exploreminsk.presentation.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.exploreminsk.domain.model.Category
import com.dev.exploreminsk.domain.model.Place
import com.dev.exploreminsk.domain.usecase.category_usecase.CategoryUseCases
import com.dev.exploreminsk.domain.usecase.place_usecase.PlaceUseCases
import com.dev.exploreminsk.domain.usecase.room_usecase.RoomPlaceUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreScreenViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases,
    private val roomPlaceUseCases: RoomPlaceUseCases,
    private val placeUseCases: PlaceUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(ExploreScreenState())
    val state: StateFlow<ExploreScreenState> = _state

    init {
        getCategories()
    }

    fun onEvent(event: ExploreScreenEvent) {
        when (event) {
            is ExploreScreenEvent.OnPlaceDoubleClick -> {
                savePlace(event.place)
            }

            is ExploreScreenEvent.OnCategorySelected -> {
                getPlacesByCategory(event.category)
                _state.update {
                    it.copy(
                        selectedCategory = event.category
                    )
                }
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            categoryUseCases.getAllCategoriesUseCase().onSuccess { categories ->
                val firstCategory = categories.firstOrNull()

                if (firstCategory != null) {
                    placeUseCases.getAllPlacesByCategory(firstCategory).onSuccess { places ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                categories = categories,
                                selectedCategory = firstCategory,
                                popularPlaces = places
                            )
                        }
                    }.onFailure { error ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                categories = categories,
                                selectedCategory = firstCategory,
                                errorMessage = error.message
                            )
                        }
                    }
                } else {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            categories = emptyList()
                        )
                    }
                }
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.message
                    )
                }
            }
        }
    }


    private fun getPlacesByCategory(category: Category) {
        viewModelScope.launch {
            placeUseCases.getAllPlacesByCategory(category)
                .onSuccess { places ->
                    _state.update {
                        it.copy(
                            popularPlaces = places
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            errorMessage = error.message
                        )
                    }
                }
        }
    }

    private fun savePlace(place: Place) {
        viewModelScope.launch {
            try {
                roomPlaceUseCases.upsertPlaceUseCase(place)
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = e.message
                    )
                }
            }
        }
    }

}