package com.example.android.airqualitypollen.presentation.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.android.airqualitypollen.databinding.FragmentDetailsAirQualityBinding
import com.example.android.airqualitypollen.business.location.boundary.GeoLocation

/**
 * The fragment for providing the AirQuality-data in the ViewPager.
 */
class DetailsFragmentAirQuality(val geoLocation: GeoLocation) : Fragment() {

    private var _binding: FragmentDetailsAirQualityBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: DetailsAirQualityViewModel by lazy {
        ViewModelProvider(this).get(DetailsAirQualityViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsAirQualityBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.selectedGeoLocation.observe(viewLifecycleOwner, { geoLocation ->
            viewModel.fetchAirQuality(geoLocation)
        })

        viewModel.updateSelectedLocation(geoLocation)
    }
}