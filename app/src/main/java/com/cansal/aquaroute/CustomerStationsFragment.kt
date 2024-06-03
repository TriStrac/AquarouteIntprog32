package com.cansal.aquaroute

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentPagerAdapter
import com.cansal.aquaroute.adapters.CustomerStationAdapter
import com.cansal.aquaroute.adapters.OrdersOwnerAdapter
import com.cansal.aquaroute.databinding.FragmentCustomerDashboardBinding
import com.cansal.aquaroute.databinding.FragmentCustomerStationsBinding

class CustomerStationsFragment : Fragment() {
    private var _binding: FragmentCustomerStationsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCustomerStationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CustomerStationAdapter(childFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        adapter.addFragment(CustomerSubscriptionFragment(), "Subscription")
        adapter.addFragment(CustomerDiscoverFragment(), "Discover")
        (activity as? CustomerDashboard)?.updateScreenName("")
        //(activity as? CustomerDashboard)?.updateMultiIcon(R.drawable.baseline_history_24)
        binding.stationsViewPager.adapter = adapter

        binding.topTab.setupWithViewPager(binding.stationsViewPager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}