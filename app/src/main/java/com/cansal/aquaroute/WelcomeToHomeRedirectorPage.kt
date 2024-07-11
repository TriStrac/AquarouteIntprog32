package com.cansal.aquaroute

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cansal.aquaroute.databinding.ActivityWelcomeToHomeRedirectorPageBinding
import com.cansal.aquaroute.storage.LocalStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WelcomeToHomeRedirectorPage : AppCompatActivity() {
    private lateinit var binding:ActivityWelcomeToHomeRedirectorPageBinding
    private val mainScope = CoroutineScope(Dispatchers.Main)

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

        val localStorage=LocalStorage(this)
        if(localStorage.appFirstOpen){
            localStorage.appFirstOpen=false
        }
        val name = getFirstName(intent.getStringExtra("loggedInName"))
        binding.welcomeText.text = "Welcome!\n\n$name"
        val type = intent.getStringExtra("loggedInType")
        val email = intent.getStringExtra("loggedInEmail")

        mainScope.launch {
            delay(1000)
            if(type == "owner"){
                val intent = Intent(this@WelcomeToHomeRedirectorPage, OwnerDashboard::class.java).apply{
                    putExtra("loggedInName", name)
                    putExtra("loggedInEmail", email)
                    putExtra("loggedInType", type)
                }
                val options = ActivityOptions.makeCustomAnimation(this@WelcomeToHomeRedirectorPage, R.anim.fade_in, R.anim.fade_out)
                startActivity(intent, options.toBundle())
                finish()
            }else if(type == "customer"){
                val intent = Intent(this@WelcomeToHomeRedirectorPage, CustomerDashboard::class.java).apply{
                    putExtra("loggedInName", name)
                    putExtra("loggedInEmail", email)
                    putExtra("loggedInType", type)
                }
                val options = ActivityOptions.makeCustomAnimation(this@WelcomeToHomeRedirectorPage, R.anim.fade_in, R.anim.fade_out)
                startActivity(intent, options.toBundle())
                finish()
            }

        }


    }
    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }
    fun getFirstName(fullName: String?): String {
        val trimmedName = fullName?.trim()
        if (trimmedName!!.isEmpty()) return ""
        return trimmedName.split(" ")[0]
    }
}