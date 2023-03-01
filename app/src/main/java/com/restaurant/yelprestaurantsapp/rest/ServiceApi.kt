package com.restaurant.yelprestaurantsapp.rest

import com.restaurant.yelprestaurantsapp.model.UserResponse
import com.restaurant.yelprestaurantsapp.model.YelpResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {

    @GET(SEARCH_PATH)
    suspend fun getHotNewRestaurants(
        @Query("latitude") latitude: Double?,
        @Query("longitude") longitude: Double?,
        @Query("term") term: String = "restaurants",
        @Query("attributes") attributes: String = "hot_and_new",
        @Query("sort_by") sort: String = "best_match",
        @Query("limit") limit: Int = 20,
    ): Response<YelpResponse>

    @GET(PATH_ID + PATH_USERS)
    suspend fun getReviews(
        @Path("id") id: String,
        @Query("sort_by") sorting: String = "yelp_sort"
    ): Response<UserResponse>

    companion object {
        //'https://api.yelp.com/v3/businesses/
        // search?latitude=33.90927894861643
        // &longitude=-84.47924964050215
        // &term=restaurants
        // &attributes=hot_and_new&sort_by=best_match&limit=20'
        const val BASE_URL = "https://api.yelp.com/v3/businesses/"
        private const val SEARCH_PATH = "search"
        private const val PATH_ID = "{id}/"
        private const val PATH_USERS = "reviews"

    }

}