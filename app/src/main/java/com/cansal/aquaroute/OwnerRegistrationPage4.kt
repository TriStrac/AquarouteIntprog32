package com.cansal.aquaroute

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cansal.aquaroute.databinding.ActivityOwnerRegistrationPage4Binding
import com.cansal.aquaroute.models.CustomerUser
import com.cansal.aquaroute.models.OrdersForOwnerToCustomer
import com.cansal.aquaroute.models.OwnerUser
import com.cansal.aquaroute.models.Subscribers
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class OwnerRegistrationPage4 : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerRegistrationPage4Binding
    private lateinit var database:FirebaseDatabase
    private lateinit var reference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOwnerRegistrationPage4Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.backButtonOwnerRegistration4.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        binding.finalOwnerRegister.setOnClickListener {
            val email = intent.getStringExtra("email")
            val password = intent.getStringExtra("password")
            val stationName = intent.getStringExtra("stationName")
            val region = intent.getStringExtra("region")
            val street = intent.getStringExtra("street")
            val unit = intent.getStringExtra("unit")
            val phone = binding.phoneNumber.text.toString().trim()
            val name = binding.nameInput.text.toString().trim()
            val type = "owner"

            binding.phoneNumber.error = null
            binding.nameInput.error = null

            var isValid = true

            if (phone.isEmpty()) {
                binding.phoneNumber.error = "Phone must not be empty"
                isValid = false
            } else if (!isValidPhoneNumber(phone)) {
                binding.phoneNumber.error = "Invalid phone number"
                isValid = false
            }

            if (name.isEmpty()) {
                binding.nameInput.error = "Name must not be empty"
                isValid = false
            }

            if (isValid) {
                createOwnerAccount(email, password, stationName, region, street, unit, phone, name, type)
                val intent = Intent(this@OwnerRegistrationPage4, LoginPage::class.java)
                val options = ActivityOptions.makeCustomAnimation(this@OwnerRegistrationPage4, R.anim.slide_in_right, R.anim.slide_out_left)
                startActivity(intent, options.toBundle())
                finish()
            }
        }

    }
    private fun isValidPhoneNumber(phone: String): Boolean {
        val phonePattern = Regex("^09\\d{9}$")
        return phonePattern.matches(phone)
    }
    private fun createOwnerAccount(
        email: String?,
        password: String?,
        stationName: String?,
        region: String?,
        street: String?,
        unit: String?,
        phone: String?,
        name: String?,
        type: String?
    ) {
        Log.d("OwnerAccount", "Email: $email")
        if (email != null) {
            val encodedEmail = encodeEmail(email)
            val database = FirebaseDatabase.getInstance()
            val reference = database.getReference("users")
            val ownerUser = OwnerUser(email, password, stationName, region, street, unit, phone, name, type)

            reference.child(encodedEmail).setValue(ownerUser).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("OwnerAccount", "Account creation succeeded for email: $email")
                } else {
                    Log.e("OwnerAccount", "Account creation failed for email: $email", task.exception)
                }
            }
            initializeSubscribers(encodedEmail)
            initializeOrders(encodedEmail)
        }
    }
    private fun initializeSubscribers(email:String){
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("subscribers")
        val subsInit = Subscribers(email,"D3F4ULT","D3F4ULT","D3F4ULT","D3F4ULT")
        reference.child(email).setValue(subsInit)
    }
    private fun initializeOrders(email:String){
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("orders")
        val ordersInit = OrdersForOwnerToCustomer("D3F4ULT",
            "D3F4ULT","D3F4ULT","D3F4ULT",
            "D3F4ULT","D3F4ULT","D3F4ULT",
            "D3F4ULT","D3F4ULT",email,"D3F4ULT",
            "D3F4ULT")
        reference.child(email).setValue(ordersInit)
    }

    fun encodeEmail(email: String): String {
        return email.replace(".", ",")
    }

}