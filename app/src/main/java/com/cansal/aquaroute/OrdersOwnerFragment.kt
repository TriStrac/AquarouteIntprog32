package com.cansal.aquaroute

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentPagerAdapter
import com.cansal.aquaroute.adapters.OrdersOwnerAdapter
import com.cansal.aquaroute.databinding.FragmentOrdersOwnerBinding

class OrdersOwnerFragment : Fragment() {
    private var _binding: FragmentOrdersOwnerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrdersOwnerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = OrdersOwnerAdapter(childFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        adapter.addFragment(OwnerForPickupOrders(), "For Pickup")
        adapter.addFragment(OwnerForDeliveryOrders(), "For Delivery")
        (activity as? OwnerDashboard)?.updateScreenName("")
        (activity as? OwnerDashboard)?.updateMultiIcon(R.drawable.baseline_history_24)
        binding.ordersViewPager.adapter = adapter

        binding.topTab.setupWithViewPager(binding.ordersViewPager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


