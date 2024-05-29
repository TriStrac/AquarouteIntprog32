package com.cansal.aquaroute

import PickUpRecyclerViewAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.cansal.aquaroute.databinding.FragmentForPickupOrdersBinding
import com.cansal.aquaroute.models.OrdersForOwnerToCustomer


class ForPickupOrders : Fragment() {

    private lateinit var binding: FragmentForPickupOrdersBinding
    private lateinit var adapter: PickUpRecyclerViewAdapter
    private lateinit var ordersList: List<OrdersForOwnerToCustomer>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForPickupOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ordersList with your data
        ordersList = listOf(
            OrdersForOwnerToCustomer("John Doe", "john@example.com", 5, "123 Main St", "Owner", "owner@example.com", "Pending", "Pending"),
            OrdersForOwnerToCustomer("Jane Smith", "jane@example.com", 4, "456 Oak St", "Owner", "owner@example.com", "Pending", "Pending")
            // Add more orders here
        )

        adapter = PickUpRecyclerViewAdapter(
            activity = requireActivity(),
            ordersList = ordersList,
            confirmPickUp = this::confirmPickUp,
            cancelPickUp = this::cancelPickUp
        )

        binding.pickUpRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.pickUpRecyclerView.adapter = adapter
    }

    private fun confirmPickUp(order: OrdersForOwnerToCustomer) {
        // Handle the confirmation of pickup
        Toast.makeText(context, "Pickup confirmed for ${order.customerName}", Toast.LENGTH_SHORT).show()
    }

    private fun cancelPickUp(order: OrdersForOwnerToCustomer) {
        // Handle the cancellation of pickup
        Toast.makeText(context, "Pickup cancelled for ${order.customerName}", Toast.LENGTH_SHORT).show()
    }
}

