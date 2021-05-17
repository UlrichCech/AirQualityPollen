package com.example.android.airqualitypollen.presentation.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.airqualitypollen.business.favorites.entity.FavoriteDTO
import com.example.android.airqualitypollen.databinding.ListItemFavoritesBinding
import com.example.android.airqualitypollen.presentation.favorites.FavoriteListClickListener

class OverviewFavoritesListAdapter(private val clickListener: FavoriteListClickListener) :
    ListAdapter<FavoriteDTO, OverviewFavoritesListAdapter.ViewHolder>(FavoriteDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemFavoritesBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }


    class ViewHolder constructor(private val binding: ListItemFavoritesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: FavoriteListClickListener, item: FavoriteDTO) {
            binding.favorite = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemFavoritesBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class FavoriteDiffCallback : DiffUtil.ItemCallback<FavoriteDTO>() {
    override fun areItemsTheSame(oldItem: FavoriteDTO, newItem: FavoriteDTO): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FavoriteDTO, newItem: FavoriteDTO): Boolean {
        return oldItem == newItem
    }
}
