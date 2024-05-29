package com.cansal.aquaroute

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cansal.aquaroute.R
import com.cansal.aquaroute.adapters.DeliveryRecyclerViewAdapter
import com.cansal.aquaroute.databinding.FragmentForDeliveryOrdersBinding
import com.cansal.aquaroute.models.OrdersForOwnerToCustomer

class ForDeliveryOrders : Fragment() {

    private var _binding: FragmentForDeliveryOrdersBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: DeliveryRecyclerViewAdapter
    private lateinit var ordersList: List<OrdersForOwnerToCustomer>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForDeliveryOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ordersList with your data
        ordersList = listOf(
            OrdersForOwnerToCustomer("John Doe", "john@example.com", 100, "123 Main St", "Owner", "owner@example.com", "Done", "Requested"),
            OrdersForOwnerToCustomer("Jane Smith", "jane@example.com", 150, "456 Oak St", "Owner", "owner@example.com", "Done", "Requested")
            // Add more orders here
        )

        // Initialize the adapter with filtered data
        adapter = DeliveryRecyclerViewAdapter(
            activity = requireActivity(),
            ordersList = ordersList.filter { it.orderPickupStatus == "Done" },
            confirmPickUp = this::confirmPickUp,
            cancelPickUp = this::cancelPickUp
        )

        // Set up the RecyclerView with the adapter
        binding.deliveryRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.deliveryRecyclerView.adapter = adapter

        binding.historyFAB.setOnClickListener {
            val intent = Intent(requireContext(), OwnerHistoryPage::class.java)
            val options = ActivityOptions.makeCustomAnimation(requireContext(), R.anim.fade_in, R.anim.fade_in)
            startActivity(intent, options.toBundle())
        }
    }

    private fun confirmPickUp(order: OrdersForOwnerToCustomer) {
        // Handle the confirmation of pickup
        Toast.makeText(context, "Pickup confirmed for ${order.customerName}", Toast.LENGTH_SHORT).show()
    }

    private fun cancelPickUp(order: OrdersForOwnerToCustomer) {
        // Handle the cancellation of pickup
        Toast.makeText(context, "Pickup cancelled for ${order.customerName}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
