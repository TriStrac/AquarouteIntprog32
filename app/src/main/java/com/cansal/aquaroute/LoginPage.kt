package com.cansal.aquaroute

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cansal.aquaroute.databinding.ActivityLoginPageBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

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
            val email = encodeEmail(binding.emailInputText.text.toString().trim())
            val password = binding.passwordInputText.text.toString().trim()
            if (validateInput(email, password)) {
                validateAccount(email, password)
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

    private fun validateAccount(email: String, password: String) {
        val reference = FirebaseDatabase.getInstance().getReference("users")
        val checkUserDatabase = reference.orderByChild("email").equalTo(email)

        checkUserDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    binding.emailInputText.error = null
                    val userSnapshot = snapshot.children.firstOrNull()
                    userSnapshot?.let {
                        val passwordFromDB = it.child("password").getValue(String::class.java)
                        if (passwordFromDB == password) {
                            binding.emailInputText.error = null
                            val nameFromDB = it.child("name").getValue(String::class.java)
                            val emailFromDB = it.child("email").getValue(String::class.java)
                            val typeFromDB = it.child("type").getValue(String::class.java)

                            val intent = Intent(this@LoginPage, WelcomeToHomeRedirectorPage::class.java).apply {
                                putExtra("loggedInName", nameFromDB)
                                putExtra("loggedInEmail", emailFromDB)
                                putExtra("loggedInType", typeFromDB)
                            }

                            val options = ActivityOptions.makeCustomAnimation(
                                this@LoginPage, R.anim.fade_in, R.anim.slide_out_left
                            )
                            startActivity(intent, options.toBundle())
                        } else {
                            binding.passwordInputText.error = "Invalid Credentials"
                            binding.passwordInputText.requestFocus()
                        }
                    }
                } else {
                    binding.emailInputText.error = "User does not exist"
                    binding.emailInputText.requestFocus()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


    fun encodeEmail(email: String): String {
        return email.replace(".", ",")
    }

    private fun validateInput(email: String, password: String): Boolean {
        var isValid = true

        binding.emailInputText.error = null
        binding.passwordInputText.error = null

        if (email.isEmpty()) {
            binding.emailInputText.error = "Email must not be empty"
            isValid = false
        }

        if (password.isEmpty()) {
            binding.passwordInputText.error = "Password must not be empty"
            isValid = false
        }

        return isValid
    }
}
