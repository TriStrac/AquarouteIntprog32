package com.cansal.aquaroute

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cansal.aquaroute.databinding.ActivityCustomerRegistrationPage2Binding
import com.cansal.aquaroute.models.CustomerStock
import com.cansal.aquaroute.models.CustomerUser
import com.cansal.aquaroute.models.OwnerUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CustomerRegistrationPage2 : AppCompatActivity() {
    private lateinit var binding: ActivityCustomerRegistrationPage2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCustomerRegistrationPage2Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.backButtonCustomerRegistration.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        binding.finalCustomerRegister.setOnClickListener {
            val email = intent.getStringExtra("emailCustomer")
            val password = intent.getStringExtra("passwordCustomer")
            val name = binding.nameInput.text.toString().trim()
            val phone = binding.phoneInput.text.toString().trim()
            val region = binding.regionInput.text.toString().trim()
            val street = binding.streetInput.text.toString().trim()
            val unit = binding.unitInput.text.toString().trim()
            val type = "customer"

            binding.nameInput.error = null
            binding.phoneInput.error = null
            binding.regionInput.error = null
            binding.streetInput.error = null
            binding.unitInput.error = null

            var isValid = true

            if (name.isEmpty()) {
                binding.nameInput.error = "Name must not be empty"
                isValid = false
            }

            if (phone.isEmpty()) {
                binding.phoneInput.error = "Phone must not be empty"
                isValid = false
            } else if (!isValidPhoneNumber(phone)) {
                binding.phoneInput.error = "Invalid phone number"
                isValid = false
            }

            if (region.isEmpty()) {
                binding.regionInput.error = "Region must not be empty"
                isValid = false
            }

            if (street.isEmpty()) {
                binding.streetInput.error = "Street must not be empty"
                isValid = false
            }

            if (unit.isEmpty()) {
                binding.unitInput.error = "Unit must not be empty"
                isValid = false
            }

            if (isValid) {
                Toast.makeText(this, email, Toast.LENGTH_SHORT).show()
                createCustomerAccount(email, password, name, phone, region, street, unit, type)
                val intent = Intent(this@CustomerRegistrationPage2, LoginPage::class.java)
                val options = ActivityOptions.makeCustomAnimation(
                    this@CustomerRegistrationPage2,
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
                )
                startActivity(intent, options.toBundle())
                finish()
            }
        }
    }
    private fun isValidPhoneNumber(phone: String): Boolean {
        val phonePattern = Regex("^09\\d{9}$")
        return phonePattern.matches(phone)
    }
    private fun createCustomerAccount(email: String?, password: String?, name: String?, phone: String?, region: String?, street: String?, unit: String?, type: String?) {
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("users")
        val email2 = email?.let { encodeEmail(it) }
        val customerUser = CustomerUser(email2, password, name, phone, region, street, unit, type)
        if (email2 != null) {
            reference.child(email2).setValue(customerUser)
            initializeCustomerStock(email2)
        }
    }

    private fun initializeCustomerStock(email:String){
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("customerStock")
        val stock=CustomerStock(email,0,0,0)
        reference.child(email).setValue(stock)
    }

    fun encodeEmail(email: String): String {
        return email.replace(".", ",")
    }
}