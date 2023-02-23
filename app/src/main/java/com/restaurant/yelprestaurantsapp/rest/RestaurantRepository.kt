package com.restaurant.yelprestaurantsapp.rest

import com.example.restaurantsapp.rest.ServiceApi
import com.restaurant.yelprestaurantsapp.utils.UIState
import com.restaurant.yelprestaurantsapp.model.domain.RestaurantDomain
import kotlinx.coroutines.flow.Flow
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
    ): Flow<UIState<List<RestaurantDomain>>> {
        TODO("Not yet implemented")
    }
}