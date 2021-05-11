package com.example.android.presentation.overview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.airqualitypollen.R
import com.example.android.business.airquality.boundary.AirQualityViewModel
import com.example.android.airqualitypollen.databinding.FragmentOverviewBinding
import com.example.android.business.airquality.boundary.AirQualityResult
import com.example.android.business.airquality.boundary.PollenResult

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: AirQualityViewModel by lazy {
        ViewModelProvider(this).get(AirQualityViewModel::class.java)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCurrentLocation.setOnClickListener {
            // TODO: fetch current location
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        viewModel.currentAirQuality.observe(viewLifecycleOwner, Observer<AirQualityResult> { airQuality ->
            Log.i("UCE-OBS", airQuality.toString())
        })
        viewModel.currentPollen.observe(viewLifecycleOwner, Observer<PollenResult> { pollen ->
            Log.i("UCE-OBS", pollen.toString())
        })

        viewModel.getAirQualityForLocation(53.699500, 10.761190)     // Ratzeburg: 53,699500, 10,761190
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        //make sure to clear the view model after destroy, as it's a single view model.
    }

}