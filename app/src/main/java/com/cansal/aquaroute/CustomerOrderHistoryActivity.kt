package com.cansal.aquaroute

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cansal.aquaroute.databinding.ActivityCustomerOrderHistoryBinding
import com.cansal.aquaroute.databinding.FragmentCustomerDashboardBinding

class CustomerOrderHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomerOrderHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCustomerOrderHistoryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.backIcon.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}