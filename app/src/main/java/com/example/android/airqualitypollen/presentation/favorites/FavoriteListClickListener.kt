package com.example.android.airqualitypollen.presentation.favorites

import com.example.android.airqualitypollen.business.favorites.entity.FavoriteDTO

class FavoriteListClickListener(val clickListener: (selectedFavorite: FavoriteDTO) -> Unit) {
    fun onClick(favorite: FavoriteDTO) = clickListener(favorite)
}
