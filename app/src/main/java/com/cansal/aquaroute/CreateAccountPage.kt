package com.cansal.aquaroute

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cansal.aquaroute.databinding.ActivityCreateAccountPageBinding

class CreateAccountPage : AppCompatActivity() {
    private lateinit var binding:ActivityCreateAccountPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCreateAccountPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.nextPageRegister.setOnClickListener {
            val intent = Intent(this@CreateAccountPage, RegisterChoicePage::class.java)
            val options = ActivityOptions.makeCustomAnimation(this@CreateAccountPage, R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent, options.toBundle())
            finish()
        }
    }
}