package com.cansal.aquaroute
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cansal.aquaroute.databinding.ActivityWelcomeToHomeRedirectorPageBinding
import com.cansal.aquaroute.storage.LocalStorage
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class WelcomeToHomeRedirectorPage : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeToHomeRedirectorPageBinding
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private lateinit var localStorage: LocalStorage
    private lateinit var database: DatabaseReference

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

        localStorage = LocalStorage(this)
        if (localStorage.appFirstOpen) {
            localStorage.appFirstOpen = false
        }
        val name = getFirstName(intent.getStringExtra("loggedInName"))
        binding.welcomeText.text = "Welcome!\n\n$name"
        val type = intent.getStringExtra("loggedInType")
        val email = intent.getStringExtra("loggedInEmail")

        mainScope.launch {
            delay(1000)
            if (type == "owner") {
                email?.let { loadOwner(it) }
                val intent = Intent(this@WelcomeToHomeRedirectorPage, OwnerDashboard::class.java).apply {
                    putExtra("loggedInName", name)
                    putExtra("loggedInEmail", email)
                    putExtra("loggedInType", type)
                }
                val options = ActivityOptions.makeCustomAnimation(this@WelcomeToHomeRedirectorPage, R.anim.fade_in, R.anim.fade_out)
                startActivity(intent, options.toBundle())
                finish()
            } else if (type == "customer") {
                email?.let { loadCustomer(it) }
                val intent = Intent(this@WelcomeToHomeRedirectorPage, CustomerDashboard::class.java).apply {
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

    private suspend fun loadOwner(email: String) {
        val databaseUsers = FirebaseDatabase.getInstance().getReference("users")
        val userSnapshot = databaseUsers.child(email).get().await()
        localStorage.loggedInRegion = userSnapshot.child("region").value.toString()
        localStorage.loggedInStreet = userSnapshot.child("street").value.toString()
        localStorage.loggedInUnit = userSnapshot.child("unit").value.toString()
        localStorage.loggedInPhoneNumber = userSnapshot.child("phone").value.toString()
        localStorage.loggedInStationName = userSnapshot.child("stationName").value.toString()
        localStorage.loggedInName = userSnapshot.child("name").value.toString()

        val databaseStationStats = FirebaseDatabase.getInstance().getReference("stationStats")
        val statsSnapshot = databaseStationStats.child(email).get().await()
        localStorage.ownerOrdersPending = statsSnapshot.child("ordersPending").value.toString()
        localStorage.ownerOrdersCompleted = statsSnapshot.child("ordersCompleted").value.toString()
        localStorage.ownerJugsInStock = statsSnapshot.child("ownerJugsInStock").value.toString()

        localStorage.loggedInEmail = decodeEmail(email)
    }

    private suspend fun loadCustomer(email: String) {
        val databaseUsers = FirebaseDatabase.getInstance().getReference("users")
        val userSnapshot = databaseUsers.child(email).get().await()

        localStorage.loggedInRegion = userSnapshot.child("region").value.toString()
        localStorage.loggedInStreet = userSnapshot.child("street").value.toString()
        localStorage.loggedInUnit = userSnapshot.child("unit").value.toString()
        localStorage.loggedInPhoneNumber = userSnapshot.child("phone").value.toString()
        localStorage.loggedInName = userSnapshot.child("name").value.toString()

        val databaseCustomerStock = FirebaseDatabase.getInstance().getReference("customerStock")
        val stockSnapshot = databaseCustomerStock.child(email).get().await()
        localStorage.customerFilledJugs = stockSnapshot.child("customerFilledJugs").value.toString()
        localStorage.customerJugsInStations = stockSnapshot.child("customerJugsInStations").value.toString()
        localStorage.customerTotalJugs = stockSnapshot.child("customerTotalJugs").value.toString()

        localStorage.loggedInEmail = decodeEmail(email)
    }

    fun decodeEmail(email: String): String {
        return email.replace(",", ".")
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
