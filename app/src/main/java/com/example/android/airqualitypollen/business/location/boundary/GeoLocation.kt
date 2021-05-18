package com.example.android.airqualitypollen.business.location.boundary

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GeoLocation(val lat: Double = 0.0, val lng: Double = 0.0) : Parcelable