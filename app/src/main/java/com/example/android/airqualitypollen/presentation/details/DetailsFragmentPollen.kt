package com.example.android.airqualitypollen.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.airqualitypollen.business.favorites.entity.FavoriteDTO
import com.example.android.airqualitypollen.databinding.FragmentDetailsPollenBinding
import com.example.android.airqualitypollen.business.location.boundary.GeoLocation

/**
 * The fragment for providing the Pollen-data in the ViewPager.
 */
class DetailsFragmentPollen() : Fragment() {

    private var _binding: FragmentDetailsPollenBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: DetailsPollenViewModel by lazy {
        ViewModelProvider(this).get(DetailsPollenViewModel::class.java)
    }

    private var geoLocation: GeoLocation? = GeoLocation()
    private var favorite: FavoriteDTO? = FavoriteDTO()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        geoLocation = arguments?.getParcelable("geoLocation")
        favorite = arguments?.getParcelable("favorite")
        // Inflate the layout for this fragment
        _binding = FragmentDetailsPollenBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchPollen.observe(viewLifecycleOwner, {
            if (it) {
                viewModel.fetchPollen(favorite, geoLocation)
            }
        })
        viewModel.prepareData(favorite)
    }


    companion object {
        fun newInstance(
            geoLocation: GeoLocation?,
            favorite: FavoriteDTO?
        ): DetailsFragmentPollen {
            val args = Bundle()
            args.putParcelable("geoLocation", geoLocation)
            args.putParcelable("favorite", favorite)
            val newFragment = DetailsFragmentPollen()
            newFragment.arguments = args
            return newFragment
        }
    }

}