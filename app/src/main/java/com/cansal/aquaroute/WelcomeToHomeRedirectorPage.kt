package com.cansal.aquaroute

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cansal.aquaroute.databinding.ActivityWelcomeToHomeRedirectorPageBinding
import com.cansal.aquaroute.storage.LocalStorage

class WelcomeToHomeRedirectorPage : AppCompatActivity() {
    private lateinit var binding:ActivityWelcomeToHomeRedirectorPageBinding
    private val localStorage= LocalStorage(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityWelcomeToHomeRedirectorPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if(localStorage.appFirstOpen){
            localStorage.appFirstOpen=false
        }
        val name = getFirstName(intent.getStringExtra("loggedInName"))
        binding.welcomeText.text = "Welcome!\n\n$name"


    }

    fun getFirstName(fullName: String?): String {
        val trimmedName = fullName?.trim()  // Remove leading/trailing spaces
        if (trimmedName!!.isEmpty()) return ""  // Return an empty string if input is empty
        return trimmedName.split(" ")[0]
    }
}