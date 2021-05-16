package com.example.android.airqualitypollen.presentation.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.airqualitypollen.databinding.FragmentDetailsBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

/**
 * This fragm,ent displays the detailled air-quality and pollen data to the selected location.
 */
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    private lateinit var fragmentAdapter: FragmentAdapter



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        fragmentAdapter =
            FragmentAdapter(requireActivity().supportFragmentManager,
                            requireActivity().lifecycle,
                            DetailsFragmentArgs.fromBundle(requireArguments()).selectedGeoLocation)
        binding.detailsViewPager.adapter = fragmentAdapter

        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                _binding?.detailsViewPager?.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToOverviewFragment())
        }
//        viewModel.selectedGeoLocation.observe(viewLifecycleOwner, { geoLocation ->
//            viewModel.fetchAirQualityAndPollen(geoLocation)
//        })
        viewModel.currentAirQuality.observe(viewLifecycleOwner, { airQuality ->
            Log.i("UCE-AIR", airQuality.toString())
        })
        viewModel.currentPollen.observe(viewLifecycleOwner, { pollen ->
            Log.i("UCE-AIR", pollen.toString())
        })

//        viewModel.updateSelectedLocation(DetailsFragmentArgs.fromBundle(requireArguments()).selectedGeoLocation)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}