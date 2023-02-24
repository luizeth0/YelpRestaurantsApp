package com.restaurant.yelprestaurantsapp.viewmodel

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.restaurant.yelprestaurantsapp.model.domain.RestaurantDomain
import com.restaurant.yelprestaurantsapp.rest.RestaurantRepository
import com.restaurant.yelprestaurantsapp.rest.RestaurantRepositoryImpl
import com.restaurant.yelprestaurantsapp.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YelpViewModel @Inject constructor(
    private val restaurantRepository: RestaurantRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

): ViewModel() {

    var name = ""
    var img = ""
    var loc = ""
    var phone = ""
    var price = ""
    var distance = 0.0
    var rating = 0.0

    private val _business: MutableLiveData<UIState<List<RestaurantDomain>>> = MutableLiveData(UIState.LOADING)
    val business: LiveData<UIState<List<RestaurantDomain>>> get() = _business

    init {
        getBusinessByLocation()
    }

    fun getBusinessByLocation(lat: Double? = null, lon: Double? = null) {

            if (lat != null && lon != null) {
                viewModelScope.launch(ioDispatcher) {
                    restaurantRepository.getRestaurants(lat,lon).collect {
                        _business.postValue(it)
                        Log.d(TAG, "getBusinessByLocation: $_business")
                    }

                }

            }



    }


}