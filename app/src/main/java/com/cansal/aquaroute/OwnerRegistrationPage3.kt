package com.cansal.aquaroute

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cansal.aquaroute.databinding.ActivityOwnerRegistrationPage3Binding

class OwnerRegistrationPage3 : AppCompatActivity() {
    private lateinit var binding:ActivityOwnerRegistrationPage3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOwnerRegistrationPage3Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.backButtonOwnerRegistration3.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        binding.continuePage4.setOnClickListener {
            val region = binding.regionInput.text.toString().trim()
            val street = binding.streetInput.text.toString().trim()
            val unit = binding.unitInput.text.toString().trim()

            binding.regionInput.error = null
            binding.streetInput.error = null
            binding.unitInput.error = null

            var isValid = true

            if (region.isEmpty()) {
                binding.regionInput.error = "Region must not be empty"
                isValid = false
            }

            if (street.isEmpty()) {
                binding.streetInput.error = "Street must not be empty"
                isValid = false
            }

            if (unit.isEmpty()) {
                binding.unitInput.error = "Unit must not be empty"
                isValid = false
            }

            if (isValid) {
                val intent = Intent(this@OwnerRegistrationPage3, OwnerRegistrationPage4::class.java).apply {
                    putExtra("email", intent.getStringExtra("email"))
                    putExtra("password", intent.getStringExtra("password"))
                    putExtra("stationName", intent.getStringExtra("stationName"))
                    putExtra("region", region)
                    putExtra("street", street)
                    putExtra("unit", unit)
                }
                val options = ActivityOptions.makeCustomAnimation(this@OwnerRegistrationPage3, R.anim.slide_in_right, R.anim.slide_out_left)
                startActivity(intent, options.toBundle())
            }
        }

    }
}