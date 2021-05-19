package com.example.android.airqualitypollen.business.nature.boundary

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchResultEntry(@Json(name="id") val id: String,
                             @Json(name = "links") val links: SearchResultEntryLinks): Parcelable