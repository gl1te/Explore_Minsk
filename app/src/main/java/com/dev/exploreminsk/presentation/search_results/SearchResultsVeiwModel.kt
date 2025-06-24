package com.dev.exploreminsk.presentation.search_results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.exploreminsk.domain.model.Category
import com.dev.exploreminsk.domain.usecase.category_usecase.CategoryUseCases
import com.dev.exploreminsk.domain.usecase.place_usecase.PlaceUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultsViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases,
    private val placeUseCases: PlaceUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(SearchResultsScreenState())
    val state = _state
    private var searchJob: Job? = null


    init {
        getCategories()
        loadPlaces()
    }


    fun onEvent(event: SearchResultsScreenEvent) {
        when (event) {
            is SearchResultsScreenEvent.OnSearchQueryChange -> {
                _state.update { it.copy(searchQuery = event.query) }
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(400)
                    applyFilters()
                }
            }
            is SearchResultsScreenEvent.OnCategorySelected -> {
                _state.update { it.copy(selectedCategory = event.category) }
                applyFilters()
            }
        }
    }


    private fun applyFilters() {
        val query = _state.value.searchQuery.trim()
        val selectedCategory = _state.value.selectedCategory

        val categoryId = if (selectedCategory == null || selectedCategory.id == -1L) null else selectedCategory.id

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            placeUseCases.getPlacesByNameAndCategory(query, categoryId)
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


    private fun getCategories() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            categoryUseCases.getAllCategoriesUseCase().onSuccess { categories ->
                val allCategory = Category(id = -1, name = "All")
                val categoryList = listOf(allCategory) +categories
                _state.update {
                    it.copy(
                        isLoading = false,
                        categories = categoryList
                    )
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

    private fun loadPlaces() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        placeUseCases.getAllPlacesUseCase().onSuccess { list ->
            _state.update { it.copy(isLoading = false, allPlaces = list) }
            applyFilters()
        }.onFailure {error->
            _state.update { it.copy(isLoading = false, errorMessage = error.message) }
        }
    }

}