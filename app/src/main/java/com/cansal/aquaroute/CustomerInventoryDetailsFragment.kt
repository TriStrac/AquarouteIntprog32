package com.cansal.aquaroute

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentPagerAdapter
import com.cansal.aquaroute.adapters.CustomerTabsAdapter
import com.cansal.aquaroute.databinding.FragmentCustomerInventoryBinding
import com.cansal.aquaroute.databinding.FragmentCustomerInventoryDetailsBinding

class CustomerInventoryDetailsFragment : Fragment() {
    private var _binding: FragmentCustomerInventoryDetailsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCustomerInventoryDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding.jug1.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}