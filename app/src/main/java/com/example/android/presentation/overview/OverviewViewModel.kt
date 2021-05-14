package com.example.android.presentation.overview

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.business.location.boundary.GeoLocation

class OverviewViewModel: ViewModel() {

    private val _navigateToDetails = MutableLiveData<Boolean>()
    val navigateToDetails: LiveData<Boolean>
        get() = _navigateToDetails

    fun onNavigateToDetailsClicked() {
        _navigateToDetails.value = true
    }

    fun onNavigateToDetailsFinished() {
        _navigateToDetails.value = false
    }


    private val _selectedGeoLocation = MutableLiveData<GeoLocation>()
    val selectedGeoLocation: LiveData<GeoLocation>
        get() = _selectedGeoLocation


    fun updateSelectedLocation(location: Location) {
        _selectedGeoLocation.value = GeoLocation(location.latitude, location.longitude)
    }

}