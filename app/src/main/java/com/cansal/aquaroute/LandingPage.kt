package com.cansal.aquaroute

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cansal.aquaroute.databinding.ActivityLandingPageBinding
import com.cansal.aquaroute.storage.LocalStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LandingPage : AppCompatActivity() {
    private lateinit var binding: ActivityLandingPageBinding
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLandingPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val localStorage = LocalStorage(this)
        if(!localStorage.appFirstOpen){
            mainScope.launch {
                delay(1000)
                val intent = Intent(this@LandingPage, LoginPage::class.java)
                val options = ActivityOptions.makeCustomAnimation(this@LandingPage, R.anim.fade_in, R.anim.fade_out)
                startActivity(intent, options.toBundle())
                finish()
            }
        }else{
            mainScope.launch {
                delay(1000)
                val intent = Intent(this@LandingPage, OnboardPage::class.java)
                val options = ActivityOptions.makeCustomAnimation(this@LandingPage, R.anim.fade_in, R.anim.fade_out)
                startActivity(intent, options.toBundle())
                finish()
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }
}
