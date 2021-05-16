package com.example.android.airqualitypollen.presentation.favorites

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.airqualitypollen.R
import com.example.android.airqualitypollen.databinding.FragmentFavoritesBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * This fragment shows a map to select a location and save it to the favorites places.
 */
class FavoritesFragment : Fragment(), OnMapReadyCallback {

    companion object {
        val TAG: String = FavoritesFragment::class.java.simpleName
    }

    private var _binding: FragmentFavoritesBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by lazy {
        ViewModelProvider(this).get(FavoritesViewModel::class.java)
    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var map: GoogleMap

    private val runningQorLater =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

//        setHasOptionsMenu(true)
//        setDisplayHomeAsUpEnabled(true)
        /**
         * SupportMapFragment is a way to get GoogleMap into the Application
         * When we reference a fragment inside an Activity we use supportFragmentManger
         * When we reference a fragment inside a parent Fragment we use childFragmentManager
         * getMapAsync is also called to prepare Google Map. When this finishes the onMapReady is called
         */
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        return binding.root
    }

    private fun onLocationSelected(latLng: LatLng) {
//        val latLng = poi.latLng
        viewModel.latitude.value = latLng.latitude
        viewModel.longitude.value = latLng.longitude
        viewModel.selectedPOI.value = latLng
//        viewModel.reminderSelectedLocationStr.value = poi.name
//        _viewModel.navigationCommand.postValue(NavigationCommand.Back)
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.map_options, menu)
//    }

    //If Location Permission has been granted we Zoom In
    @SuppressLint("MissingPermission")
    private fun enableLocation() {
        if (foregroundAndBackgroundLocationPermissionApproved()) {
            map.isMyLocationEnabled = true
            fusedLocationProviderClient.lastLocation.addOnSuccessListener(requireActivity()) { location ->
                if (location != null) {
                    val userLatLng = LatLng(location.latitude, location.longitude)
                    val zoomLevel = 15f
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, zoomLevel))
                }
            }
        } else {
//            _viewModel.showErrorMessage.postValue(getString(R.string.err_select_location))
//            ActivityCompat.requestPermissions(
//                requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                REQUEST_TURN_LOCATION_ON
//            )
        }
    }

    @TargetApi(Build.VERSION_CODES.Q)
    private fun foregroundAndBackgroundLocationPermissionApproved(): Boolean {
//        val backgroundLocationApproved =
//            if (runningQorLater) {
//                PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
//                    requireContext(),
//                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
//                )
//            } else {
//                true
//            }
        return (
                PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
                ))
    }

    //Change the Map Type based on User selection
//    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
//        R.id.normal_map -> {
//            map.mapType = GoogleMap.MAP_TYPE_NORMAL
//            true
//        }
//        R.id.hybrid_map -> {
//            map.mapType = GoogleMap.MAP_TYPE_HYBRID
//            true
//        }
//        R.id.satellite_map -> {
//            map.mapType = GoogleMap.MAP_TYPE_SATELLITE
//            true
//        }
//        R.id.terrain_map -> {
//            map.mapType = GoogleMap.MAP_TYPE_TERRAIN
//            true
//        }
//        else -> super.onOptionsItemSelected(item)
//    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        enableLocation()

        setClickListeners(map)

//        setMapStyle(map)
    }

    //POI (Point of Interest) that clicks and sets a Pin displaying POI name.
    private fun setClickListeners(map: GoogleMap) {
//        map.setOnPoiClickListener { poi ->
//            binding.saveLocationButton.visibility = View.VISIBLE
//
//            val poiMarket = map.addMarker(
//                MarkerOptions().position(poi.latLng)
//                    .title(poi.name)
//                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
//            )
//            val zoom = 16f
//            map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi.latLng, zoom))
//            poiMarket.showInfoWindow()
//
//            binding.saveLocationButton.setOnClickListener {
//                onLocationSelected(poi)
//            }
//        }

        map.setOnMapLongClickListener { latLng ->
            binding.saveLocationButton.visibility = View.VISIBLE
            map.clear()
            viewModel.setNewSelectedMarker(map.addMarker(
                MarkerOptions()
                    .position(latLng)
            ))
            val zoom = 16f
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
            binding.saveLocationButton.setOnClickListener {
                onLocationSelected(latLng)
            }
        }

    }

//    private fun setMapStyle(map: GoogleMap) {
//        try {
//            //Customize the Base Map Style using a JSON object defined in the raw res file.
//            val success = map.setMapStyle(
//                MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style)
//            )
//            if (!success) {
//                Log.e(TAG, "Style Parsing Failed.")
//            }
//        } catch (e: Resources.NotFoundException) {
//            Log.e(TAG, "Can't find Style. Error", e)
//        }
//    }

}