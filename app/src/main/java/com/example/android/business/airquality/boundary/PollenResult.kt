package com.example.android.business.airquality.boundary

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PollenResult(val message: String,
                        val data: List<PollenResultData>) : Parcelable {}
