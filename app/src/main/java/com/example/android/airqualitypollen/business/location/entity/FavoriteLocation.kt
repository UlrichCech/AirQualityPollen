package com.example.android.airqualitypollen.business.location.entity

import androidx.room.Entity

@Entity
data class FavoriteLocation(
    val lat: Double,
    val lng: Double,
    val description: String)
