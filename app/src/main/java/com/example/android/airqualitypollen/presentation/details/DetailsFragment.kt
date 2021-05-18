package com.example.android.airqualitypollen.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.airqualitypollen.R
import com.example.android.airqualitypollen.databinding.FragmentDetailsBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator


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
                            DetailsFragmentArgs.fromBundle(requireArguments()).selectedGeoLocation,
                            DetailsFragmentArgs.fromBundle(requireArguments()).selectedFavorite)
        binding.detailsViewPager.adapter = fragmentAdapter
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                _binding?.detailsViewPager?.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                // currently not needed/implemented
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                // currently not needed/implemented
            }
        })

        TabLayoutMediator(binding.tabLayout, binding.detailsViewPager) {
                tab, position -> tab.text = getTabText(position)
        }.attach()

        return binding.root
    }


    private fun getTabText(position: Int) : String {
        return when (position) {
            1 -> resources.getString(R.string.tab_label_pollen)
            else -> resources.getString(R.string.tab_label_air_quality)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToOverviewFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}