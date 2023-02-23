package com.restaurant.yelprestaurantsapp.model


import com.google.gson.annotations.SerializedName
import com.restaurant.yelprestaurantsapp.model.Center

data class Region(
    @SerializedName("center")
    val center: Center? = null
)