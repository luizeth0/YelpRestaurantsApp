package com.restaurant.yelprestaurantsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.restaurant.yelprestaurantsapp.databinding.FragmentHomeBinding

private const val TAG = "Home"

class Home : Fragment() {

    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding.toolbarLayout.title = getString(R.string.app_name)


        return binding.root
    }
}