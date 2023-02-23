package com.restaurant.yelprestaurantsapp.model


import com.google.gson.annotations.SerializedName
import com.restaurant.yelprestaurantsapp.model.Business
import com.restaurant.yelprestaurantsapp.model.Region

data class YelpResponse(
    @SerializedName("businesses")
    val businesses: List<Business>? = null,
    @SerializedName("region")
    val region: Region? = null,
    @SerializedName("total")
    val total: Int? = null
)