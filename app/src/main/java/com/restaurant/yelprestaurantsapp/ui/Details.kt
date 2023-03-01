package com.restaurant.yelprestaurantsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.restaurant.yelprestaurantsapp.R
import com.restaurant.yelprestaurantsapp.databinding.ContentScrollingBinding
import com.restaurant.yelprestaurantsapp.databinding.FragmentDetailsBinding
import com.restaurant.yelprestaurantsapp.model.User
import com.restaurant.yelprestaurantsapp.model.domain.ReviewDomain
import com.restaurant.yelprestaurantsapp.ui.adapter.YelpAdapter
import com.restaurant.yelprestaurantsapp.utils.UIState
import com.restaurant.yelprestaurantsapp.utils.ViewType
import com.restaurant.yelprestaurantsapp.viewmodel.YelpViewModel

class Details : Fragment() {

    private val binding by lazy {
        FragmentDetailsBinding.inflate(layoutInflater)
    }

    private val yelpAdapter by lazy {
        YelpAdapter {}
    }

    private val yelpViewModel by lazy {
        ViewModelProvider(requireActivity())[YelpViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.rvReview.apply {
            layoutManager =
                LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false)
            adapter = yelpAdapter
        }

        getRestaurantDetails()
        yelpViewModel.getReviews(yelpViewModel.id)
        getRestaurantReviews()

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

    private fun getRestaurantReviews() {
        yelpViewModel.reviews.observe(viewLifecycleOwner) { state ->
            val ViewTypeList: MutableList<ViewType> = mutableListOf()
            when (state) {
                is UIState.LOADING -> {
                }
                is UIState.SUCCESS<List<ReviewDomain>> -> {
                    if (state.response.isEmpty()){
                        var emptyList = ReviewDomain("",0.0,User("","","",""),"","","")
                        ViewTypeList.add((ViewType.REVIEW(emptyList)))
                    } else {
                        state.response.forEach {
                            ViewTypeList.add(ViewType.REVIEW(it))

                        }
                    }

                    yelpAdapter.updateItems(ViewTypeList)
                }
                is UIState.ERROR -> {
                    state.error.localizedMessage?.let {
                        throw Exception("Error")
                    }
                }
            }
        }
    }

}

