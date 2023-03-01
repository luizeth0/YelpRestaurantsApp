package com.restaurant.yelprestaurantsapp.model


import com.google.gson.annotations.SerializedName
import com.restaurant.yelprestaurantsapp.model.Review

data class UserResponse(
    @SerializedName("possible_languages")
    val possibleLanguages: List<String?>? = null,
    @SerializedName("reviews")
    val reviews: List<Review>? = null,
    @SerializedName("total")
    val total: Int? = null
)