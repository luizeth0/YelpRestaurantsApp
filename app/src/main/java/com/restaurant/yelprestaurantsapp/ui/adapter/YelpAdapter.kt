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
import com.restaurant.yelprestaurantsapp.model.domain.RestaurantDomain

class YelpAdapter(
    private val itemSet: MutableList<RestaurantDomain> = mutableListOf(),
    private val onItemClick: (RestaurantDomain) -> Unit
) : RecyclerView.Adapter<YelpViewHolder>() {


    fun updateItems(newItems: List<RestaurantDomain>) {
        if (itemSet != newItems) {
            itemSet.clear()
            itemSet.addAll(newItems)

            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YelpViewHolder =
        YelpViewHolder(
            RestaurantViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false

            )
        )


    override fun getItemCount(): Int = itemSet.size

    override fun onBindViewHolder(holder: YelpViewHolder, position: Int) =
        holder.bind(itemSet[position], onItemClick)


}



class YelpViewHolder(private val binding: RestaurantViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RestaurantDomain, onItemClick: (RestaurantDomain) -> Unit) {

        binding.nameRestaurant.text = item.name
        binding.addressRestaurant.text = item.location.displayAddress.toString().replace("[","").replace("]","")
        //binding.priceRestaurant.text = item.price
        binding.distanceRestaurant.text = "${item.distance.toString()} m"
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