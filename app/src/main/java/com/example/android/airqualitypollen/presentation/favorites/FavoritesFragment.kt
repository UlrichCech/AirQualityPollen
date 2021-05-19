package com.example.android.airqualitypollen.presentation.favorites

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.android.airqualitypollen.R
import com.example.android.airqualitypollen.business.airquality.boundary.AmbeeApi
import com.example.android.airqualitypollen.business.configuration.GlobalAppConfiguration
import com.example.android.airqualitypollen.business.favorites.entity.FavoriteDTO
import com.example.android.airqualitypollen.databinding.FragmentFavoritesBinding
import com.example.android.airqualitypollen.platform.persistence.EntityManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

/**
 * This fragment shows a map to select a location and save it to the favorites places.
 */
class FavoritesFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentFavoritesBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by lazy {
        ViewModelProvider(this).get(FavoritesViewModel::class.java)
    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var map: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        /**
         * SupportMapFragment is a way to get GoogleMap into the Application
         * When we reference a fragment inside an Activity we use supportFragmentManger
         * When we reference a fragment inside a parent Fragment we use childFragmentManager
         * getMapAsync is also called to prepare Google Map. When this finishes the onMapReady is called
         */
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        binding.saveLocationButton.setOnClickListener {
            viewModel.viewModelScope.launch {
                val newFavorite = FavoriteDTO(
                    latitude = viewModel.selectedLatLng.value?.latitude,
                    longitude = viewModel.selectedLatLng.value?.longitude)
                val r1 = async {
                    try {
                        val airQuality =
                            AmbeeApi.RETROFIT_SERVICE.getAirQualityForCurrentLocation(
                                newFavorite.latitude.toString(), newFavorite.longitude.toString(), GlobalAppConfiguration.ambeeApiKey!!).toEntity()
                        newFavorite.updateAirQuality(airQuality)
                    } catch (e: Exception) {
                        Log.e("UCE", e.message, e)
                    }
                }
                val r2 = async {
                    try {
                        val pollen =
                            AmbeeApi.RETROFIT_SERVICE.getPollenForCurrentLocation(
                                newFavorite.latitude.toString(),
                                newFavorite.longitude.toString(),
                                GlobalAppConfiguration.ambeeApiKey!!
                            )
                        newFavorite.updatePollen(pollen.toEntity())
                    } catch (e: Exception) {
                        Log.e("UCE", e.message, e)
                    }
                }
                listOf(r1, r2).awaitAll()
                EntityManager.getFavoriteDao().saveFavorite(newFavorite)
                findNavController().navigate(FavoritesFragmentDirections.actionFavoritesFragmentToOverviewFragment())
            }
        }
        return binding.root
    }

    @SuppressLint("MissingPermission")
    private fun enableLocation() {
        if (fineLocationPermissionApproved()) {
            map.isMyLocationEnabled = true
            fusedLocationProviderClient.lastLocation.addOnSuccessListener(requireActivity()) { location ->
                if (location != null) {
                    val userLatLng = LatLng(location.latitude, location.longitude)
                    val zoomLevel = 15f
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, zoomLevel))
                }
            }
        } else {
            viewModel.showErrorMessage.postValue(getString(R.string.no_fine_location_permission))
        }
    }

    @TargetApi(Build.VERSION_CODES.Q)
    private fun fineLocationPermissionApproved(): Boolean {
        return (
                PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
                ))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        enableLocation()
        map.setOnMapLongClickListener { latLng ->
            map.clear()
            viewModel.selectedLatLng.value = latLng
            viewModel.setNewSelectedMarker(map.addMarker(
                MarkerOptions()
                    .position(latLng)
            ))
        }
    }

}