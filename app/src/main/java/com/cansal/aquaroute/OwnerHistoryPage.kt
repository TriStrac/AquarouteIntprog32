package com.cansal.aquaroute

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.cansal.aquaroute.adapters.HistoryRecyclerViewAdapter
import com.cansal.aquaroute.databinding.ActivityOwnerHistoryPageBinding
import com.cansal.aquaroute.models.OrdersForOwnerToCustomer
import androidx.core.view.WindowInsetsCompat

class OwnerHistoryPage : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerHistoryPageBinding
    private lateinit var adapter: HistoryRecyclerViewAdapter
    private lateinit var ordersList: List<OrdersForOwnerToCustomer>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOwnerHistoryPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.backIcon.setOnClickListener {
            finish()
        }

        // Initialize ordersList with your data
        val ordersList = listOf(
            OrdersForOwnerToCustomer(
                "John Doe",
                "john.doe@example.com",
                "5",
                (5 * 20).toString(),
                "123 Main St",
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


        // Filter the ordersList
        val filteredOrders = ordersList.filter {
            it.orderPickupStatus == "Cancelled" || it.orderDeliveryStatus == "Completed"
        }

        // Initialize the adapter with filtered data
        adapter = HistoryRecyclerViewAdapter(activity = this, ordersList = filteredOrders)

        // Set up the RecyclerView with the adapter
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.historyRecyclerView.adapter = adapter
    }

    private fun enableEdgeToEdge() {
        // Your implementation to enable edge-to-edge content
    }
}
