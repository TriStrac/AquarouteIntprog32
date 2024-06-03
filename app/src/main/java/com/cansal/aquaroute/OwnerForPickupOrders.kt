package com.cansal.aquaroute

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.cansal.aquaroute.adapters.PickUpRecyclerViewAdapter
import com.cansal.aquaroute.databinding.FragmentForPickupOrdersBinding
import com.cansal.aquaroute.models.OrdersForOwnerToCustomer


class OwnerForPickupOrders : Fragment() {

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
        val ordersList = listOf(
            OrdersForOwnerToCustomer(
                "John Doe",
                "john.doe@example.com",
                "5",
                (5 * 20).toString(),
                "123 Main St, Cogon, Coeased fasdfasddas",
                "2023-06-01",
                "6:00 PM",
                "Owner 1",
                "owner1@example.com",
                "Pending",
                "Pending"
            ),
            OrdersForOwnerToCustomer(
                "Jane Smith",
                "jane.smith@example.com",
                "3",
                (3 * 20).toString(),
                "456 Elm St",
                "2023-06-02",
                "5:00 PM",
                "Owner 2",
                "owner2@example.com",
                "Pending",
                "Pending"
            ),
            OrdersForOwnerToCustomer(
                "Robert Brown",
                "robert.brown@example.com",
                "2",
                (2 * 20).toString(),
                "789 Oak St",
                "2023-06-03",
                "4:00 PM",
                "Owner 3",
                "owner3@example.com",
                "Pending",
                "Pending"
            ),
            OrdersForOwnerToCustomer(
                "Emily Johnson",
                "emily.johnson@example.com",
                "4",
                (4 * 20).toString(),
                "101 Pine St",
                "2023-06-04",
                "3:00 PM",
                "Owner 4",
                "owner4@example.com",
                "Pending",
                "Pending"
            ),
            OrdersForOwnerToCustomer(
                "Michael Williams",
                "michael.williams@example.com",
                "6",
                (6 * 20).toString(),
                "202 Birch St",
                "2023-06-05",
                "2:00 PM",
                "Owner 5",
                "owner5@example.com",
                "Pending",
                "Pending"
            )
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

