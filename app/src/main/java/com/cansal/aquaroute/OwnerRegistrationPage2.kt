package com.cansal.aquaroute

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cansal.aquaroute.databinding.ActivityOwnerRegistrationPage2Binding

class OwnerRegistrationPage2 : AppCompatActivity() {
    private lateinit var binding:ActivityOwnerRegistrationPage2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOwnerRegistrationPage2Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.backButtonOwnerRegistration2.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        binding.continuePage3.setOnClickListener {
            val stationName = binding.stationNameInput.text.toString().trim()
            binding.stationNameInput.error = null

            var isValid = true

            if (stationName.isEmpty()) {
                binding.stationNameInput.error = "Station name must not be empty"
                isValid = false
            }

            if(isValid){
                val intent = Intent(this@OwnerRegistrationPage2, OwnerRegistrationPage3::class.java).apply {
                    putExtra("email", intent.getStringExtra("email"))
                    putExtra("password", intent.getStringExtra("password"))
                    putExtra("stationName",stationName)
                }
                val options = ActivityOptions.makeCustomAnimation(this@OwnerRegistrationPage2, R.anim.slide_in_right, R.anim.slide_out_left)
                startActivity(intent, options.toBundle())
            }
        }
    }
}