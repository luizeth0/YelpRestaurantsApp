package com.restaurant.yelprestaurantsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.restaurant.yelprestaurantsapp.R
import com.restaurant.yelprestaurantsapp.databinding.ContentScrollingBinding
import com.restaurant.yelprestaurantsapp.databinding.FragmentDetailsBinding
import com.restaurant.yelprestaurantsapp.viewmodel.YelpViewModel

class Details : Fragment() {

    private val binding by lazy {
        FragmentDetailsBinding.inflate(layoutInflater)
    }


    private val yelpViewModel by lazy {
        ViewModelProvider(requireActivity())[YelpViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        getRestaurantDetails()



        return binding.root
    }


    private fun getRestaurantDetails() {
        binding.nameDetails.text = yelpViewModel.name
        binding.addresDetails.text = yelpViewModel.loc
        binding.phoneDetails.text = yelpViewModel.phone
        binding.ratingDetail.rating = yelpViewModel.rating.toFloat()

        Glide
            .with(binding.root)
            .load(yelpViewModel.img)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(binding.imgDetails)
    }
}

