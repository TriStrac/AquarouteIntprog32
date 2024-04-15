package com.cansal.aquaroute

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.cansal.aquaroute.databinding.ActivityHomePageBinding

class HomePage : AppCompatActivity() {
    private lateinit var binding: ActivityHomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.screenName.text="Deliveries"
        replaceFragment(DeliveriesFragment())

        binding.accountIcon.setOnClickListener {
            intent = Intent(this,AccountPage::class.java)
            startActivity(intent)
        }

        binding.bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.delivery1 -> {
                    binding.screenName.text="Deliveries"
                    replaceFragment(DeliveriesFragment())
                    true
                }
                R.id.client2 -> {
                    binding.screenName.text="Clients"
                    replaceFragment(ClientsFragment())
                    true
                }
                R.id.analytic3 -> {
                    binding.screenName.text="Analytics"
                    replaceFragment(AnalyticsFragment())
                    true
                }
                R.id.transaction4 -> {
                    binding.screenName.text="Transactions"
                    replaceFragment(TransactionsFragment())
                    true
                }
                else -> false
            }
        }
    }
    fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()

    }

}