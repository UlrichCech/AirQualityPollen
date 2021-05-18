package com.example.android.airqualitypollen.presentation.overview

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.airqualitypollen.business.favorites.entity.FavoriteDTO
import com.example.android.airqualitypollen.business.location.boundary.GeoLocation
import com.example.android.airqualitypollen.platform.persistence.EntityManager
import kotlinx.coroutines.launch

class OverviewViewModel: ViewModel() {

    private val _selectedGeoLocation = MutableLiveData<GeoLocation>()
    val selectedGeoLocation: LiveData<GeoLocation>
        get() = _selectedGeoLocation

    private val _selectedFavorite = MutableLiveData<FavoriteDTO>()
    val selectedFavorite: LiveData<FavoriteDTO>
        get() = _selectedFavorite

    private val _navigateToDetails = MutableLiveData<Boolean>()
    val navigateToDetails: LiveData<Boolean>
        get() = _navigateToDetails

    private val _navigateToAddFavorite = MutableLiveData<Boolean>()
    val navigateToAddFavorite: LiveData<Boolean>
        get() = _navigateToAddFavorite

    private val _favoritesList = MutableLiveData<List<FavoriteDTO>>()
    val favoritesList: LiveData<List<FavoriteDTO>>
        get() = _favoritesList

    val showErrorMessage: MutableLiveData<String> = MutableLiveData()


    init {
        viewModelScope.launch {
            try {
                val listResult = EntityManager.getFavoriteDao().getAllFavorites()
                if (listResult.isNotEmpty()) {
                    _favoritesList.value = listResult
                }
            } catch (e: Exception) {
                _favoritesList.value = ArrayList()
            }
        }
    }


    fun deleteFavorite(id: String) {
        viewModelScope.launch {
            try {
                EntityManager.getFavoriteDao().deleteFavoriteById(id)
                val listResult = EntityManager.getFavoriteDao().getAllFavorites()
                if (listResult.isNotEmpty()) {
                    _favoritesList.value = listResult
                } else {
                    _favoritesList.value = ArrayList()
                }
            } catch (e: Exception) {
                Log.e("UCE", e.message, e)
            }
        }
    }



    fun updateSelectedLocation(location: Location) {
        _selectedGeoLocation.value = GeoLocation(location.latitude, location.longitude)
    }

    fun updateSelectedFavorite(favorite: FavoriteDTO) {
        _selectedFavorite.value = favorite
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