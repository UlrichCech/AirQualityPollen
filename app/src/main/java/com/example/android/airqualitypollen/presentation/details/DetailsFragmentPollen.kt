package com.example.android.airqualitypollen.presentation.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.airqualitypollen.databinding.FragmentDetailsPollenBinding
import com.example.android.airqualitypollen.business.location.boundary.GeoLocation

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragmentPollen.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsFragmentPollen(val geoLocation: GeoLocation) : Fragment() {

    private var _binding: FragmentDetailsPollenBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: DetailsPollenViewModel by lazy {
        ViewModelProvider(this).get(DetailsPollenViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsPollenBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentPollen.observe(viewLifecycleOwner, { pollen ->
            Log.i("UCE-AIR", pollen.toString())
        })

        viewModel.selectedGeoLocation.observe(viewLifecycleOwner, { geoLocation ->
            viewModel.fetchPollen(geoLocation)
        })

        viewModel.updateSelectedLocation(geoLocation)
    }
}