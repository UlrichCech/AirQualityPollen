package com.example.android.airqualitypollen.business.nature.boundary

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchResult(@Json(name="total") val total: Int,
                        @Json(name = "total_pages") val totalPages: Int,
                        @Json(name = "results") val results : List<SearchResultEntry>): Parcelable