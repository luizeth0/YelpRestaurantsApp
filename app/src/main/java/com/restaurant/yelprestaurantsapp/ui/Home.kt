package com.restaurant.yelprestaurantsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.restaurant.yelprestaurantsapp.R
import com.restaurant.yelprestaurantsapp.databinding.FragmentHomeBinding
import com.restaurant.yelprestaurantsapp.model.domain.RestaurantDomain
import com.restaurant.yelprestaurantsapp.ui.adapter.YelpAdapter
import com.restaurant.yelprestaurantsapp.utils.UIState
import com.restaurant.yelprestaurantsapp.viewmodel.YelpViewModel


private const val TAG = "Home"

class Home : Fragment() {

    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    /*private val bindingConScro by lazy {
        ContentScrollingBinding.inflate(layoutInflater)
    }*/


    private val yelpViewModel: YelpViewModel by lazy {
        ViewModelProvider(requireActivity())[YelpViewModel::class.java]
    }

    private val yelpAdapter by lazy {
        YelpAdapter {
            yelpViewModel.name = it.name
            yelpViewModel.img = it.image
            yelpViewModel.loc = it.location.displayAddress.toString().replace("[","").replace("]","")
            yelpViewModel.price = it.price
            yelpViewModel.rating = it.rating
            yelpViewModel.distance = it.distance
            yelpViewModel.phone = it.phone
            findNavController().navigate(R.id.action_navigation_home_to_navigation_details)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        binding.rvRestaurants.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false)

            adapter = yelpAdapter
        }


        yelpViewModel.getBusinessByLocation(33.9091094, -84.4813835)
        getBusiness()



        return binding.root
    }


    private fun getBusiness() {
        yelpViewModel.business.observe(viewLifecycleOwner) {
            when (it) {
                is UIState.LOADING -> {
                }
                is UIState.SUCCESS<List<RestaurantDomain>> -> {
                    yelpAdapter.updateItems(it.response ?: emptyList())
                }
                is UIState.ERROR -> {
                    it.error.localizedMessage?.let {
                        throw Exception("Error")
                    }
                }
                else -> {}
            }
        }
    }


}