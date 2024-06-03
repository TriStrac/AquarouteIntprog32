package com.cansal.aquaroute

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.cansal.aquaroute.adapters.OwnerContactsRecyclerViewAdapter
import com.cansal.aquaroute.models.OrdersForOwnerToCustomer
import com.cansal.aquaroute.databinding.ActivityOwnerContactsBinding

class OwnerContacts : AppCompatActivity() {

    private lateinit var binding: ActivityOwnerContactsBinding
    private lateinit var adapter: OwnerContactsRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOwnerContactsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)


        binding.backIcon.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
        // Create sample data
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

        // Initialize adapter
        adapter = OwnerContactsRecyclerViewAdapter(this, ordersList)

        // Set up RecyclerView
        binding.contactsRecyclerView.adapter = adapter
        binding.contactsRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}
