package com.example.android.airqualitypollen.presentation.overview

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.airqualitypollen.business.favorites.entity.FavoriteDTO

@BindingAdapter("favoritesList")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<FavoriteDTO>?) {
    val adapter = recyclerView.adapter as OverviewFavoritesListAdapter
    adapter.submitList(data)
}


@BindingAdapter("stationLocation")
fun bindStationLocation(locationTV: TextView, favorite: FavoriteDTO?) {
    locationTV.text = if (favorite != null) "${favorite.lat}, ${favorite.lng}" else ""
}

@BindingAdapter("placeInfo")
fun bindPlaceInfo(descriptionTV: TextView, favorite: FavoriteDTO?) {
    val data = ArrayList<String>()
    if (favorite == null) {
        descriptionTV.text = ""
        return
    }
    if (favorite.placeName != null) {
        data.add(favorite.placeName!!)
    }
    if (favorite.postalCode != null) {
        data.add(favorite.postalCode!!)
    }
    if (favorite.division != null) {
        data.add(favorite.division!!)
    }
    if (favorite.state != null) {
        data.add(favorite.state!!)
    }
    if (favorite.countryCode != null) {
        data.add(favorite.countryCode!!)
    }
    descriptionTV.text = data.joinToString(separator = ", ")
}
