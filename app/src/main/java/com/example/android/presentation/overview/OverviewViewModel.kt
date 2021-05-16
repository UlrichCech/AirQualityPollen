package com.example.android.presentation.overview

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.business.location.boundary.GeoLocation

class OverviewViewModel: ViewModel() {

    private val _selectedGeoLocation = MutableLiveData<GeoLocation>()
    val selectedGeoLocation: LiveData<GeoLocation>
        get() = _selectedGeoLocation

    private val _navigateToDetails = MutableLiveData<Boolean>()
    val navigateToDetails: LiveData<Boolean>
        get() = _navigateToDetails

    private val _navigateToAddFavorite = MutableLiveData<Boolean>()
    val navigateToAddFavorite: LiveData<Boolean>
        get() = _navigateToAddFavorite




    fun updateSelectedLocation(location: Location) {
        _selectedGeoLocation.value = GeoLocation(location.latitude, location.longitude)
    }

    fun onNavigateToDetailsClicked() {
        _navigateToDetails.value = true
    }

    fun onNavigateToDetailsFinished() {
        _navigateToDetails.value = false
    }

    fun onNavigateToAddFavoriteClicked() {
        _navigateToAddFavorite.value = true
    }

    fun onNavigateToAddFavoriteFinished() {
        _navigateToAddFavorite.value = false
    }

}