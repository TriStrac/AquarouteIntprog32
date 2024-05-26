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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern

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
            val intent = Intent(this@OwnerRegistrationPage, RegisterChoicePage::class.java)
            val options = ActivityOptions.makeCustomAnimation(this@OwnerRegistrationPage, R.anim.slide_in_left, R.anim.slide_out_right)
            startActivity(intent, options.toBundle())
            finish()
        }

        binding.continuePage2.setOnClickListener {
            val email = binding.emailInputText.text.toString()
            val password = binding.passwordInputText.text.toString()


            binding.emailInputText.error = null
            binding.passwordInputText.error = null

            var isValid = true

            if (email.isEmpty()) {
                binding.emailInputText.error = "Email must not be empty"
                isValid = false
            } else if (!isValidEmail(email)) {
                binding.emailInputText.error = "Invalid email format"
                isValid = false
            }

            if (password.isEmpty()) {
                binding.passwordInputText.error = "Password must not be empty"
                isValid = false
            } else if (!isValidPassword(password)) {
                binding.passwordInputText.error = "Password must be at least 8 characters, include at least 1 number and 1 special character"
                isValid = false
            }

            if (isValid) {
                val intent = Intent(this@OwnerRegistrationPage, OwnerRegistrationPage2::class.java).apply {
                    putExtra("emailCustomer", email)
                    putExtra("passwordCustomer", password)
                }
                val options = ActivityOptions.makeCustomAnimation(this@OwnerRegistrationPage, R.anim.slide_in_right, R.anim.slide_out_left)
                startActivity(intent, options.toBundle())
            }
        }

    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[!@#\$%^&*]).{8,}$"
        val pattern = Pattern.compile(passwordPattern)
        return pattern.matcher(password).matches()
    }
}