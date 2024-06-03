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
import com.cansal.aquaroute.adapters.DeliveryRecyclerViewAdapter
import com.cansal.aquaroute.databinding.FragmentForDeliveryOrdersBinding
import com.cansal.aquaroute.models.OrdersForOwnerToCustomer

class OwnerForDeliveryOrders : Fragment() {

    private var _binding: FragmentForDeliveryOrdersBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: DeliveryRecyclerViewAdapter

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
        val ordersList = listOf(
            OrdersForOwnerToCustomer(
                "John Doe",
                "john.doe@example.com",
                "1234567890",
                "5",
                "$100",
                "123 Main St",
                "2023-06-01",
                "6:00 PM",
                "Owner 1",
                "owner1@example.com",
                "Cancelled",
                "Pending"
            ),
            OrdersForOwnerToCustomer(
                "Jane Smith",
                "jane.smith@example.com",
                "0987654321",
                "3",
                "$60",
                "456 Elm St",
                "2023-06-02",
                "5:00 PM",
                "Owner 2",
                "owner2@example.com",
                "Cancelled",
                "Pending"
            ),
            OrdersForOwnerToCustomer(
                "Robert Brown",
                "robert.brown@example.com",
                "5678901234",
                "2",
                "$40",
                "789 Oak St",
                "2023-06-03",
                "4:00 PM",
                "Owner 3",
                "owner3@example.com",
                "Cancelled",
                "Pending"
            ),
            OrdersForOwnerToCustomer(
                "Emily Johnson",
                "emily.johnson@example.com",
                "3456789012",
                "4",
                "$80",
                "101 Pine St",
                "2023-06-04",
                "3:00 PM",
                "Owner 4",
                "owner4@example.com",
                "Cancelled",
                "Pending"
            ),
            OrdersForOwnerToCustomer(
                "Michael Williams",
                "michael.williams@example.com",
                "6789012345",
                "6",
                "$120",
                "202 Birch St",
                "2023-06-05",
                "2:00 PM",
                "Owner 5",
                "owner5@example.com",
                "Cancelled",
                "Pending"
            )
        )


        // Initialize the adapter with filtered data
        adapter = DeliveryRecyclerViewAdapter(
            activity = requireActivity(),
            ordersList = ordersList,
            confirmPickUp = this::confirmPickUp,
            cancelPickUp = this::cancelPickUp,
        )

        // Set up the RecyclerView with the adapter
        binding.deliveryRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.deliveryRecyclerView.adapter = adapter

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
