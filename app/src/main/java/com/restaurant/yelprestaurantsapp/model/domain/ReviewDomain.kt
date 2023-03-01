package com.restaurant.yelprestaurantsapp.model.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.restaurant.yelprestaurantsapp.model.Review
import com.restaurant.yelprestaurantsapp.model.User


@Entity(tableName = "reviews")
data class ReviewDomain(
    @PrimaryKey
    val id: String,
    val rating: Double,
    val user: User,
    val urlImage: String,
    val date: String,
    val review: String
)

fun List<Review>?.mapToReviews(): List<ReviewDomain> =
    this?.map {
        ReviewDomain(
            id = it.id ?: "ID not available",
            urlImage = it.url ?: "Image not available",
            user = it.user ?: User(
                id = "Id not available",
                name = "Name not available",
                profileUrl = "",
                imageUrl = ""
            ),
            date = it.timeCreated ?: "Date not available",
            review = it.text ?: "Review not available",
            rating = it.rating ?: 0.0
        )
    } ?: emptyList()