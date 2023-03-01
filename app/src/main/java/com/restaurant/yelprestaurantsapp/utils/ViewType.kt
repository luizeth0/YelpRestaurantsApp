package com.restaurant.yelprestaurantsapp.utils

import com.restaurant.yelprestaurantsapp.model.domain.RestaurantDomain
import com.restaurant.yelprestaurantsapp.model.domain.ReviewDomain

sealed class ViewType {
    data class RESTAURANT(val restaurantList: RestaurantDomain) : ViewType()
    data class REVIEW(val reviewList: ReviewDomain) : ViewType()
}
