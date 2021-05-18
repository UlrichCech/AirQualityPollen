package com.example.android.airqualitypollen.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.airqualitypollen.business.favorites.entity.FavoriteDTO
import com.example.android.airqualitypollen.business.location.boundary.GeoLocation
import com.example.android.airqualitypollen.databinding.FragmentDetailsAirQualityBinding

/**
 * The fragment for providing the AirQuality-data in the ViewPager.
 */
class DetailsFragmentAirQuality() : Fragment() {

    private var _binding: FragmentDetailsAirQualityBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: DetailsAirQualityViewModel by lazy {
        ViewModelProvider(this).get(DetailsAirQualityViewModel::class.java)
    }

    private var geoLocation: GeoLocation? = GeoLocation()
    private var favorite: FavoriteDTO? = FavoriteDTO()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        geoLocation = arguments?.getParcelable("geoLocation")
        favorite = arguments?.getParcelable("favorite")
        // Inflate the layout for this fragment
        _binding = FragmentDetailsAirQualityBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchAirQuality.observe(viewLifecycleOwner, {
            if (it) {
                viewModel.fetchAirQuality(favorite, geoLocation)
            }
        })
        viewModel.prepareData(favorite)
    }

    companion object {
        fun newInstance(
            geoLocation: GeoLocation?,
            favorite: FavoriteDTO?
        ): DetailsFragmentAirQuality {
            val args = Bundle()
            args.putParcelable("geoLocation", geoLocation)
            args.putParcelable("favorite", favorite)
            val newFragment = DetailsFragmentAirQuality()
            newFragment.arguments = args
            return newFragment
        }
    }

}