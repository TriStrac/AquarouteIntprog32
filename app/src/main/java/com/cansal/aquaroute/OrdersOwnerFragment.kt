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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = FragmentOrdersOwnerBinding.inflate(layoutInflater)

        binding.topTab.setupWithViewPager(binding.ordersViewPager)

        val adapter = OrdersOwnerAdapter(childFragmentManager,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        adapter.addFragment(ForPickupOrders(),"For Pickup")
        adapter.addFragment(ContactsOrders(),"Contacts")

        binding.ordersViewPager.adapter=adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

