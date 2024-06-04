package com.cansal.aquaroute

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentPagerAdapter
import com.cansal.aquaroute.adapters.CustomerTabsAdapter
import com.cansal.aquaroute.databinding.FragmentCustomerInventoryBinding
import com.cansal.aquaroute.databinding.FragmentCustomerStationsBinding

class CustomerInventoryFragment : Fragment() {
    private var _binding: FragmentCustomerInventoryBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCustomerInventoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CustomerTabsAdapter(childFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        adapter.addFragment(CustomerInventoryDetailsFragment(), "Inventory")
        adapter.addFragment(CustomerOrdersFragment(), "Orders")
        (activity as? CustomerDashboard)?.updateScreenName("")
        //(activity as? CustomerDashboard)?.updateMultiIcon(R.drawable.baseline_history_24)
        binding.inventoryViewPager.adapter = adapter

        binding.topTab.setupWithViewPager(binding.inventoryViewPager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}