package com.example.android.airqualitypollen.business.nature.boundary

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchResultEntryLinks(@Json(name="self") val self: String,
                                  @Json(name = "html") val html: String,
                                  @Json(name = "download") val download : String): Parcelable