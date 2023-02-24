package com.restaurant.yelprestaurantsapp.di

import com.restaurant.yelprestaurantsapp.rest.RestaurantRepository
import com.restaurant.yelprestaurantsapp.rest.RestaurantRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun providesYelpRepositoryImpl(
        yelpRepositoryImpl: RestaurantRepositoryImpl
    ): RestaurantRepository
}