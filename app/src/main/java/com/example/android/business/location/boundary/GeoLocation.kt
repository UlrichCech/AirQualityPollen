package com.example.android.business.location.boundary

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GeoLocation(val lat: Double, val lng: Double) : Parcelable