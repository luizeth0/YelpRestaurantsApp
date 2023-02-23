package com.restaurant.yelprestaurantsapp.utils

class NullYelpResponse(message: String = "Yelp response is null") : Exception(message)
class FailureResponse(message: String?) : Exception(message)