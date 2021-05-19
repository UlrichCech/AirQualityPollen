package com.example.android.airqualitypollen.presentation.overview

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.airqualitypollen.R
import com.example.android.airqualitypollen.business.favorites.entity.FavoriteDTO
import com.example.android.airqualitypollen.business.nature.boundary.UnsplashApiStatus

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


//@BindingAdapter("unsplashImageUrl")
//fun bindImage(imgView: ImageView, imgUrl: String?) {
//    imgUrl?.let {
//        val imgUri = imgUrl.toUri()     //.buildUpon().scheme("http").build()
//
//        Picasso.get()
//            .load(imgUri)
//            .placeholder(R.drawable.ic_baseline_perm_media_24)
//            .error(R.drawable.ic_connection_error)
//            .into(imgView)
////        val drawToBitmap = imgView.drawToBitmap() // TODO: perhaps getting bitmap and store to offline cache?
//    }
//}


@BindingAdapter("unsplashApiStatus")
fun bindStatus(statusImageView: ImageView, status: UnsplashApiStatus?) {
    when (status) {
        UnsplashApiStatus.LOADING -> {
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        UnsplashApiStatus.ERROR -> {
            statusImageView.setImageResource(R.drawable.ic_connection_error)
            statusImageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
        }
        UnsplashApiStatus.DONE -> {
            statusImageView.scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }
}
