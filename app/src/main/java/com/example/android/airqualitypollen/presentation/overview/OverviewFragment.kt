package com.example.android.airqualitypollen.presentation.overview

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.android.airqualitypollen.business.favorites.entity.FavoriteDTO
import com.example.android.airqualitypollen.databinding.FragmentOverviewBinding
import com.example.android.airqualitypollen.platform.persistence.EntityManager
import com.example.android.airqualitypollen.presentation.favorites.FavoriteListClickListener
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class OverviewFragment : Fragment() {

    companion object {
        val TAG = OverviewFragment::class.java.simpleName
        val LOCATION_PERMISSION_REQUEST = 1
        val LOCATION_PERMISSION = "android.permission.ACCESS_FINE_LOCATION"
    }

//    private val runningQorLater = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q

    private var _binding: FragmentOverviewBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        _binding!!.viewModel = viewModel
        _binding!!.lifecycleOwner = viewLifecycleOwner
        binding.favoritesRV.adapter = OverviewFavoritesListAdapter(FavoriteListClickListener {
            Log.i("UCE", "PlaceInfo: ${it.placeInfo}")
        })
        requestForegroundPermissions()
        checkDeviceLocationSettings(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigateToDetails.observe(viewLifecycleOwner,
            Observer<Boolean> { shouldNavigateToDetails ->
                if (shouldNavigateToDetails == true) {
                    findNavController().navigate(
                        OverviewFragmentDirections.actionOverviewFragmentToDetailsFragment(viewModel.selectedGeoLocation.value!!))
                    viewModel.onNavigateToDetailsFinished()
                }
            })
        viewModel.navigateToAddFavorite.observe(viewLifecycleOwner,
            Observer<Boolean> { shouldNavigateToAddFavorite ->
                if (shouldNavigateToAddFavorite == true) {
                    findNavController().navigate(
                        OverviewFragmentDirections.actionOverviewFragmentToFavoritesFragment())
                    viewModel.onNavigateToAddFavoriteFinished()
                }
            })

        binding.btnCurrentLocation.setOnClickListener {
            requestLastLocationOrStartLocationUpdates(TargetNavigation.DETAILS)
        }
        binding.btnAddFavorite.setOnClickListener {
            requestLastLocationOrStartLocationUpdates(TargetNavigation.ADD_FAVORITE)
        }
//        viewModel.viewModelScope.launch {
//            val allFavorites = EntityManager.getFavoriteDao().getAllFavorites()
//            Log.i("UCE_DB", "Anzahl favorites ${allFavorites.size}")
//
//            val favoriteDTO = FavoriteDTO(53.699500, 10.761190)
//            val favorite = favoriteDTO
//
//            EntityManager.getFavoriteDao().saveFavorite(favorite)
//
//            val favorite2 = EntityManager.getFavoriteDao().getFavoriteById(favorite.id)
//            Log.i("UCE_DB", favorite2.toString())
//
//            Log.i("UCE_DB", "Anzahl favorites ${EntityManager.getFavoriteDao().getAllFavorites()}")
//
//            EntityManager.getFavoriteDao().deleteAllFavorites()
//
//            Log.i("UCE_DB", "Anzahl favorites ${EntityManager.getFavoriteDao().getAllFavorites()}")
//
//            val a: Int
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        //make sure to clear the view model after destroy, as it's a single view model.
    }

    private fun foregroundLocationPermissionApproved(): Boolean {
        return (
                PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
                ))
    }

    private fun requestForegroundPermissions() {
        //Check if the permission have been already approved, if so we don't ask again and return
        if (foregroundLocationPermissionApproved()) {
            return
        }
        val permissionArray = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        val resultCode = REQUEST_FOREGROUND_ONLY_PERMISSION_REQUEST_CODE
        Log.d(TAG, "Request Foreground only location permission")
        ActivityCompat.requestPermissions(requireActivity(), permissionArray, resultCode)
    }

//    private fun requestBackgroundPermissions() {
//        //Check if the permission have been already approved, if so we don't ask again and return
//        if (foregroundAndBackgroundLocationPermissionApproved()) {
//            return
//        }
//        var permissionArray = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
//        val resultCode = when {
//            runningQorLater -> {
//                permissionArray += Manifest.permission.ACCESS_BACKGROUND_LOCATION
//                REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_RESULT_CODE
//            }
//            else -> REQUEST_FOREGROUND_ONLY_PERMISSION_REQUEST_CODE
//        }
//        Log.d(TAG, "Request Foreground only location permission")
//        ActivityCompat.requestPermissions(requireActivity(), permissionArray, resultCode)
//    }

    @SuppressLint("MissingPermission")
    private fun checkDeviceLocationSettings(resolve: Boolean = true) {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_LOW_POWER
        }
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        //LocationServices to get the Settings Client and create a val
        // called locationSettingsResponseTask to check the location settings
        val settingsClient = LocationServices.getSettingsClient(requireActivity())
        val locationSettingsResponseTask = settingsClient.checkLocationSettings(builder.build())

        locationSettingsResponseTask.addOnFailureListener { exception ->
            if (exception is ResolvableApiException && resolve) {
                try {
                    exception.startResolutionForResult(
                        requireActivity(),
                        REQUEST_TURN_LOCATION_ON
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    Log.d(TAG, "Error getting location Settings" + sendEx.message)
                }
            } else {
//                Snackbar.make(
//                    binding.saveReminderLayout,
//                    com.google.android.gms.location.R.string.location_required_error, Snackbar.LENGTH_INDEFINITE
//                ).setAction(android.R.string.ok) {
//                    checkDeviceLocationSettings()
//                }.show()
            }
        }
        locationSettingsResponseTask.addOnCompleteListener {
            if (it.isSuccessful) {
                Log.v(TAG, "Location ON")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == REQUEST_TURN_LOCATION_ON) {
            checkDeviceLocationSettings(false)
        }
    }

    private fun requestLastLocationOrStartLocationUpdates(targetNavigation: TargetNavigation) {
        // if we don't have permission ask for it and wait until the user grants it
        if (ContextCompat.checkSelfPermission(requireContext(), LOCATION_PERMISSION)
            != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission()
            return
        }
        val locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
            5000L, 5f, FindLocationAndRedirectListener(viewModel, targetNavigation))
    }

    private fun requestLocationPermission() {
        requestPermissions(arrayOf(LOCATION_PERMISSION), LOCATION_PERMISSION_REQUEST)
    }

//    private fun startLocationUpdates(fusedLocationClient: FusedLocationProviderClient) {
//        // if we don't have permission ask for it and wait until the user grants it
//        if (ContextCompat.checkSelfPermission(
//                requireContext(), LOCATION_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
//            requestLocationPermission()
//            return
//        }
//        val request = LocationRequest().setPriority(LocationRequest.PRIORITY_LOW_POWER)
//        val callback = object: LocationCallback() {
//            override fun onLocationResult(locationResult: LocationResult?) {
//                val location = locationResult?.lastLocation ?: return
//                viewModel.updateSelectedLocation(location)
//            }
//        }
//        fusedLocationClient.requestLocationUpdates(request, callback, null)
//    }

}


class FindLocationAndRedirectListener(private val viewModel: OverviewViewModel, private val targetNavigation: TargetNavigation) : android.location.LocationListener {

    override fun onLocationChanged(location: Location) {
        viewModel.updateSelectedLocation(location)
        if (targetNavigation == TargetNavigation.DETAILS) {
            viewModel.onNavigateToDetailsClicked()
        } else if (targetNavigation == TargetNavigation.ADD_FAVORITE) {
            viewModel.onNavigateToAddFavoriteClicked()
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        Log.i("UCE", "deprecated")
    }
}

enum class TargetNavigation {
    DETAILS, ADD_FAVORITE
}

private const val REQUEST_FOREGROUND_ONLY_PERMISSION_REQUEST_CODE = 22
private const val REQUEST_TURN_LOCATION_ON = 23
