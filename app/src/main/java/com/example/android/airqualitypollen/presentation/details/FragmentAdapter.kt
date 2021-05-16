package com.example.android.airqualitypollen.presentation.details

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.android.airqualitypollen.business.location.boundary.GeoLocation

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, val geoLocation: GeoLocation) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> DetailsFragmentPollen(geoLocation)
            else -> DetailsFragmentAirQuality(geoLocation)
        }
    }
}