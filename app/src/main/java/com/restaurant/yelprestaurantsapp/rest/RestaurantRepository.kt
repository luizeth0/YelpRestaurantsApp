package com.restaurant.yelprestaurantsapp.rest

import com.restaurant.yelprestaurantsapp.utils.UIState
import com.restaurant.yelprestaurantsapp.model.domain.RestaurantDomain
import com.restaurant.yelprestaurantsapp.model.domain.mapToDomainRestaurants
import com.restaurant.yelprestaurantsapp.utils.FailureResponse
import com.restaurant.yelprestaurantsapp.utils.NullYelpResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface RestaurantRepository {
    suspend fun getRestaurants(lat: Double, lon: Double): Flow<UIState<List<RestaurantDomain>>>

}

class RestaurantRepositoryImpl @Inject constructor(
    private val serviceApi: ServiceApi
) : RestaurantRepository {

    override suspend fun getRestaurants(
        lat: Double,
        lon: Double
    ): Flow<UIState<List<RestaurantDomain>>> = flow {

        emit(UIState.LOADING)
        try {
            val response = serviceApi.getHotNewRestaurants(lat, lon)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(UIState.SUCCESS(it.businesses.mapToDomainRestaurants()))
                } ?: throw NullYelpResponse() //check if response was null
            } else throw FailureResponse(response.errorBody()?.string())
        } catch (e: Exception) {
            emit(UIState.ERROR(e))
        }

    }
}