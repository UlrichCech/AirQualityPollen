package com.example.android.business.airquality.boundary

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PollenResultRiskData(@Json(name = "grass_pollen") val grassPollen: String?,
                                @Json(name = "tree_pollen") val treePollen: String?,
                                @Json(name = "weed_pollen") val weedPollen: String?) : Parcelable {}