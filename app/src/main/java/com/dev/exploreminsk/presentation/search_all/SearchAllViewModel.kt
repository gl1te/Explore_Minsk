package com.dev.exploreminsk.presentation.search_all

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.exploreminsk.domain.model.Category
import com.dev.exploreminsk.domain.usecase.place_usecase.PlaceUseCases
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchAllViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val placesUseCase: PlaceUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(SearchAllState())
    val state = _state
    private var searchJob: Job? = null

    init {
        val encodedCategoryJson: String? = savedStateHandle.get<String>("categoryJson")
        val category = encodedCategoryJson?.let { Gson().fromJson(it, Category::class.java) }
        if (category != null) {
            _state.update {
                it.copy(
                    selectedCategory = category
                )
            }

            if (category.id != -1L) {
                getPlacesByCategory(category)
            } else {
                getAllPlaces()
            }
        }

    }

    fun onEvent(event: SearchAllScreenEvent){
        when(event) {
            is SearchAllScreenEvent.OnSearchQueryChange -> {
                state.update { it.copy(searchQuery = event.query) }
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(400)
                    applyFilters()
                }
            }
        }
    }

    private fun applyFilters() {
        val query = _state.value.searchQuery.trim()
        val categoryId = _state.value.selectedCategory?.id

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            placesUseCase.getPlacesByNameAndCategory(query, categoryId)
                .onSuccess { places ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            searchedPlaces = places,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message,
                            searchedPlaces = emptyList()
                        )
                    }
                }
        }
    }


    private fun getPlacesByCategory(category: Category) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            placesUseCase.getAllPlacesByCategory(category)
                .onSuccess { places ->
                    _state.update {
                        it.copy(
                            places = places,
                            isLoading = false
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            errorMessage = error.message,
                            isLoading = false
                        )
                    }
                }
        }
    }

    private fun getAllPlaces() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            placesUseCase.getAllPlacesUseCase()
                .onSuccess { places ->
                    _state.update {
                        it.copy(
                            places = places,
                            searchedPlaces = places,
                            isLoading = false
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            errorMessage = error.message,
                             isLoading = false
                        )
                    }
                }
        }
    }

}