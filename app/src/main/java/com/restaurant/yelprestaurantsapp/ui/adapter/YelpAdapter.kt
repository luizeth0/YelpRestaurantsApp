package com.restaurant.yelprestaurantsapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.restaurant.yelprestaurantsapp.R
import com.restaurant.yelprestaurantsapp.databinding.RestaurantViewItemBinding
import com.restaurant.yelprestaurantsapp.databinding.ReviewViewItemBinding
import com.restaurant.yelprestaurantsapp.model.domain.RestaurantDomain
import com.restaurant.yelprestaurantsapp.model.domain.ReviewDomain
import com.restaurant.yelprestaurantsapp.utils.ViewType

class YelpAdapter(
    private val itemSet: MutableList<ViewType> = mutableListOf(),
    private val onItemClick: (RestaurantDomain) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    fun updateItems(newItems: List<ViewType>) {
        if (itemSet != newItems) {
            itemSet.clear()
            itemSet.addAll(newItems)

            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            YelpViewHolder(
                RestaurantViewItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false

                )
            )
        } else {
            ReviewViewHolder(
                ReviewViewItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (itemSet[position]) {
            is ViewType.RESTAURANT -> 0
            is ViewType.REVIEW -> 1
        }

    override fun getItemCount(): Int = itemSet.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = itemSet[position]) {
            is ViewType.RESTAURANT -> (holder as YelpViewHolder).bind(
                item.restaurantList,
                onItemClick
            )
            is ViewType.REVIEW -> (holder as ReviewViewHolder).bindReview(
                item.reviewList,
            )
        }
    }

}

class YelpViewHolder(private val binding: RestaurantViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RestaurantDomain, onItemClick: (RestaurantDomain) -> Unit) {

        binding.nameRestaurant.text = item.name
        binding.addressRestaurant.text = item.location.displayAddress.toString().replace("[","").replace("]","")
        //binding.priceRestaurant.text = item.price
        binding.distanceRestaurant.text = "${item.distance} miles"
        binding.ratingBar.rating = item.rating.toFloat()

        Glide
            .with(binding.root)
            .load(item.image)
            .transform(CenterCrop(), RoundedCorners(10))
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(binding.imgRestaurant)
        itemView.setOnClickListener {
            onItemClick(item)
        }
    }

}

class ReviewViewHolder(private val binding: ReviewViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("CheckResult")
    fun bindReview(item: ReviewDomain) {

        binding.userReview.text = item.user.name.toString()
        binding.dateReview.text = item.date
        binding.review.text = item.review
        binding.ratingBarReview.rating = item.rating.toFloat()


        Glide
            .with(binding.root)
            .load(item.user.imageUrl)
            .transform(CenterCrop(), RoundedCorners(60))
            .placeholder(R.drawable.icon_person)
            .error(R.drawable.icon_person)
            .into(binding.imgUserReview)
        itemView.setOnClickListener {
        }
    }

}