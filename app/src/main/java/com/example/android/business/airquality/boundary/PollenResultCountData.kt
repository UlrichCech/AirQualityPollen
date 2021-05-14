package com.example.android.business.airquality.boundary

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class PollenResultCountData(@Json(name = "grass_pollen") val grassPollen: Double?,
                                 @Json(name = "tree_pollen") val treePollen: Double?,
                                 @Json(name = "weed_pollen") val weedPollen: Double?) : Parcelable {}