package com.dev.exploreminsk.data.repository

import com.dev.exploreminsk.data.dto.PlaceDto
import com.dev.exploreminsk.domain.model.Category
import com.dev.exploreminsk.domain.model.Place
import com.dev.exploreminsk.domain.repository.CategoryRepository
import com.dev.exploreminsk.domain.repository.PlacesRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import kotlinx.atomicfu.TraceBase.None.append
import javax.inject.Inject
import kotlin.time.Duration.Companion.days

class PlacesRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val categoryRepository: CategoryRepository,
) : PlacesRepository {

    override suspend fun getPlaces(): Result<List<Place>> {
        return try {
            val rawPlaces = supabaseClient.postgrest
                .from("places")
                .select()
                .decodeList<PlaceDto>()

            val enrichedPlaces = rawPlaces.mapNotNull { placeDto ->
                val signedUrlResponse = supabaseClient.storage
                    .from("places")
                    .createSignedUrl(path = placeDto.image_link, expiresIn = 7.days)

                val categoryResult = categoryRepository.getCategory(placeDto.category_id)
                val category = categoryResult.getOrNull()

                category?.let {
                    Place(
                        id = placeDto.id,
                        name = placeDto.name,
                        rating = placeDto.rating,
                        reviews = placeDto.reviews,
                        description = placeDto.description,
                        imageLink = signedUrlResponse,
                        sourceLink = placeDto.source_link,
                        recommendedSightseeingTime = placeDto.recommended_sightseeing_time,
                        budget = placeDto.budget,
                        openTime = placeDto.open_time,
                        address = placeDto.address,
                        phone = placeDto.phone,
                        facilities = placeDto.facilities,
                        category = it
                    )
                }
            }

            Result.success(enrichedPlaces)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPlacesByCategory(category: Category): Result<List<Place>> {
        return try {
            val rawPlaces = supabaseClient.postgrest
                .from("places")
                .select {
                    filter {
                        eq("category_id", category.id)
                    }
                }
                .decodeList<PlaceDto>()

            val enrichedPlaces = rawPlaces.mapNotNull { placeDto ->
                val signedUrlResponse = supabaseClient.storage
                    .from("places")
                    .createSignedUrl(path = placeDto.image_link, expiresIn = 7.days)

                val categoryResult = categoryRepository.getCategory(placeDto.category_id)
                val selectedCategory = categoryResult.getOrNull()

                selectedCategory?.let {
                    Place(
                        id = placeDto.id,
                        name = placeDto.name,
                        rating = placeDto.rating,
                        reviews = placeDto.reviews,
                        description = placeDto.description,
                        imageLink = signedUrlResponse,
                        sourceLink = placeDto.source_link,
                        recommendedSightseeingTime = placeDto.recommended_sightseeing_time,
                        budget = placeDto.budget,
                        openTime = placeDto.open_time,
                        address = placeDto.address,
                        phone = placeDto.phone,
                        facilities = placeDto.facilities,
                        category = it
                    )
                }
            }

            Result.success(enrichedPlaces)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPlacesByNameAndCategory(query: String, categoryId: Long?): Result<List<Place>> {
        return try {
            val allPlaces = supabaseClient.postgrest.from("places")
                .select()
                .decodeList<PlaceDto>()

            val filtered = allPlaces.filter { place ->
                val matchesName = place.name.contains(query, ignoreCase = true)
                val matchesCategory = categoryId == null || place.category_id == categoryId
                matchesName && matchesCategory
            }

            val enrichedPlaces = filtered.map { placeDto ->
                val signedUrl = supabaseClient.storage
                    .from("places")
                    .createSignedUrl(path = placeDto.image_link, expiresIn = 7.days)

                val categoryResult = categoryRepository.getCategory(placeDto.category_id)
                val category = categoryResult.getOrNull() ?: return@map null

                Place(
                    id = placeDto.id,
                    name = placeDto.name,
                    rating = placeDto.rating,
                    reviews = placeDto.reviews,
                    description = placeDto.description,
                    imageLink = signedUrl,
                    sourceLink = placeDto.source_link,
                    recommendedSightseeingTime = placeDto.recommended_sightseeing_time,
                    budget = placeDto.budget,
                    openTime = placeDto.open_time,
                    address = placeDto.address,
                    phone = placeDto.phone,
                    facilities = placeDto.facilities,
                    category = category
                )
            }.filterNotNull()

            Result.success(enrichedPlaces)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



}