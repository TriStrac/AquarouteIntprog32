package com.cansal.aquaroute

import android.app.Activity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cansal.aquaroute.databinding.ActivityOwnerHomeStationProfileBinding
import com.cansal.aquaroute.storage.LocalStorage
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class OwnerHomeStationProfile : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerHomeStationProfileBinding
    private lateinit var database: DatabaseReference
    private lateinit var localStorage: LocalStorage

    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOwnerHomeStationProfileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        localStorage = LocalStorage(this)

        binding.stationInput.setText(localStorage.loggedInStationName)
        binding.phoneInput.setText(localStorage.loggedInPhoneNumber)
        binding.regionInput.setText(localStorage.loggedInRegion)
        binding.streetInput.setText(localStorage.loggedInStreet)
        binding.unitInput.setText(localStorage.loggedInUnit)

        binding.saveButton.setOnClickListener {
            mainScope.launch {
                val station = binding.stationInput.text.toString()
                val phone = binding.phoneInput.text.toString()
                val region = binding.regionInput.text.toString()
                val street = binding.streetInput.text.toString()
                val unit = binding.unitInput.text.toString()

                saveProfile(station, phone, region, street, unit)
                reloadProfile(encodeEmail(localStorage.loggedInEmail))
                setResult(Activity.RESULT_OK)
                finish()
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }

        binding.backIcon.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }

    private fun encodeEmail(email: String): String {
        return email.replace(".", ",")
    }


    private suspend fun saveProfile(station: String, phone: String, region: String, street: String, unit: String) {
        return withContext(Dispatchers.IO) {
            database = FirebaseDatabase.getInstance().getReference("users")
            val user = mapOf(
                "stationName" to station,
                "phone" to phone,
                "region" to region,
                "street" to street,
                "unit" to unit
            )
            database.child(encodeEmail(localStorage.loggedInEmail)).updateChildren(user).await()
        }
    }

    private suspend fun reloadProfile(email: String) {
        withContext(Dispatchers.IO) {
            database = FirebaseDatabase.getInstance().getReference("users")
            val dataSnapshot = database.child(email).get().await()

            withContext(Dispatchers.Main) {
                localStorage.loggedInRegion = dataSnapshot.child("region").value.toString()
                localStorage.loggedInStreet = dataSnapshot.child("street").value.toString()
                localStorage.loggedInUnit = dataSnapshot.child("unit").value.toString()
                localStorage.loggedInPhoneNumber = dataSnapshot.child("phone").value.toString()
                localStorage.loggedInStationName = dataSnapshot.child("stationName").value.toString()
                localStorage.loggedInName = dataSnapshot.child("name").value.toString()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }
}
