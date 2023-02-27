package com.restaurant.yelprestaurantsapp.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.restaurant.yelprestaurantsapp.R
import com.restaurant.yelprestaurantsapp.databinding.FragmentHomeBinding
import com.restaurant.yelprestaurantsapp.model.domain.RestaurantDomain
import com.restaurant.yelprestaurantsapp.ui.adapter.YelpAdapter
import com.restaurant.yelprestaurantsapp.utils.UIState
import com.restaurant.yelprestaurantsapp.viewmodel.YelpViewModel


private const val TAG = "Home"

class Home : Fragment(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var maps: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitude = 0.0
    private var longitude = 0.0

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
    }

    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocationServices.getFusedLocationProviderClient(requireActivity()).also {
            fusedLocationClient = it
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

        //yelpViewModel.getBusinessByLocation(33.90907588253681, -84.47932439447749)

        getBusiness()
        Log.d(TAG, "Location2: $latitude + $longitude")

        mapView = binding.maps
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        return binding.root
    }



    private fun getLocation() {
        if ((ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) &&
            (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED))  {
            fusedLocationClient.lastLocation
                .addOnSuccessListener {
                    it?.let {
                        latitude = it.latitude
                        longitude = it.longitude
                        Log.d(TAG, "Location is: $latitude + $longitude")
                    }
                }
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
        }
        yelpViewModel.getBusinessByLocation(latitude, longitude)
    }


    private fun getBusiness() {
        yelpViewModel.business.observe(viewLifecycleOwner) {
            when (it) {
                is UIState.LOADING -> {
                }
                is UIState.SUCCESS<List<RestaurantDomain>> -> {
                    Log.d(TAG, "TEST: $it")
                    yelpAdapter.updateItems(it.response ?: emptyList())
                }
                is UIState.ERROR -> {
                    it.error.localizedMessage?.let {
                        throw Exception("Error")
                    }
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        googleMap.uiSettings.isZoomControlsEnabled = true
        //val loc = LatLng(33.90907588253681, -84.47932439447749)
        yelpViewModel.getBusinessByLocation(latitude, longitude)
        val loc = LatLng(latitude,longitude)
        googleMap.addMarker(MarkerOptions().position(loc).title("You're here!"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc))
        googleMap.clear()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }


}