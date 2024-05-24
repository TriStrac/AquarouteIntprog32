package com.cansal.aquaroute

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cansal.aquaroute.databinding.ActivityLoginPageBinding
import com.cansal.aquaroute.databinding.ActivityOwnerRegistrationPageBinding

class OwnerRegistrationPage : AppCompatActivity() {
    private lateinit var binding:ActivityOwnerRegistrationPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOwnerRegistrationPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.backButtonOwnerRegistration.setOnClickListener {
            val intent = Intent(this@OwnerRegistrationPage, RegisterChoicePage::class.java).apply {
                putExtra("targetPage", 2)
            }
            val options = ActivityOptions.makeCustomAnimation(this@OwnerRegistrationPage, R.anim.slide_in_left, R.anim.slide_out_right)
            startActivity(intent, options.toBundle())
            finish()
        }

    }
}