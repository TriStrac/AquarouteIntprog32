package com.cansal.aquaroute

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cansal.aquaroute.databinding.ActivityLoginPageBinding

class LoginPage : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.loginButton.setOnClickListener {
            if(successfulLogin()){
                intent = Intent(this,HomePage::class.java)
                startActivity(intent)
            }
        }

        binding.backButtonLogin.setOnClickListener {
            val intent = Intent(this@LoginPage, OnboardPage::class.java).apply {
                putExtra("targetPage", 2)
            }
            val options = ActivityOptions.makeCustomAnimation(this@LoginPage, R.anim.slide_in_left, R.anim.slide_out_right)
            startActivity(intent, options.toBundle())
            finish()
        }
        binding.registrationButton.setOnClickListener {
            val intent = Intent(this@LoginPage, RegisterChoicePage::class.java).apply {
                putExtra("targetPage", 2)
            }
            val options = ActivityOptions.makeCustomAnimation(this@LoginPage, R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent, options.toBundle())
            finish()
        }
    }

    private fun successfulLogin(): Boolean {
        return true
    }
}