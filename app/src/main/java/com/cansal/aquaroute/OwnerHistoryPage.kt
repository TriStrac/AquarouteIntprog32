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
        ordersList = listOf(
            OrdersForOwnerToCustomer("John Doe", "john@example.com", 100, "123 Main St", "Owner", "owner@example.com", "Cancelled", "Completed"),
            OrdersForOwnerToCustomer("Jane Smith", "jane@example.com", 150, "456 Oak St", "Owner", "owner@example.com", "Done", "Completed")
            // Add more orders here
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
