package com.restaurant.yelprestaurantsapp.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
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

    lateinit var maps: GoogleMap
    private lateinit var mapView: MapView
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 0
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
        //Log.d(TAG, "Location2: $latitude + $longitude")

        mapView = binding.maps
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)



        return binding.root
    }

    private fun getPermission(){
        if ((ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) )  {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
            return getPermission()
        } else {
            fusedLocationClient.lastLocation
                .addOnSuccessListener {
                    it?.let {
                        maps.isMyLocationEnabled = true
                        getLocation(it)
                    }
                }

        }
    }

    fun getLocation(loc: Location) {
        val latitude = loc.latitude
        val longitude = loc.longitude
        Log.d(TAG, "Location is: $latitude + $longitude")
        yelpViewModel.getBusinessByLocation(latitude, longitude)
        maps.clear()
        maps.mapType = GoogleMap.MAP_TYPE_NORMAL
        maps.uiSettings.isZoomControlsEnabled = true
        maps.isMyLocationEnabled = true
        maps.addMarker(MarkerOptions().position(LatLng(latitude,longitude)).title("You're here!🙋🏻‍♂️"))
        maps.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    latitude,
                    longitude
                ), 12.0f
            )
        )
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
        maps = googleMap
        getPermission()
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