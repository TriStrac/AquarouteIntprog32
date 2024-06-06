package com.cansal.aquaroute

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.cansal.aquaroute.databinding.ActivityCustomerDashboardBinding

class CustomerDashboard : AppCompatActivity() {
    private lateinit var binding: ActivityCustomerDashboardBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCustomerDashboardBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.dashboardBG)
        toggle = ActionBarDrawerToggle(this, binding.main, R.string.open, R.string.close)
        binding.main.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val name = intent.getStringExtra("loggedInName")
        binding.screenName.text="Good Day, $name"
        replaceFragment(CustomerDashboardFragment())

        val navHeaderView = binding.navView.inflateHeaderView(R.layout.nav_header)
        val sideBarName: TextView = navHeaderView.findViewById(R.id.sideBarName)
        val sideBarEmail: TextView = navHeaderView.findViewById(R.id.sideBarEmail)

        sideBarName.text = intent.getStringExtra("loggedInName")
        sideBarEmail.text = intent.getStringExtra("loggedInEmail")

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_settings -> openSettings()
                R.id.nav_sign_out -> signOutUser()
            }
            true
        }

        binding.hamburger.setOnClickListener {
            if (binding.main.isDrawerOpen(binding.navView)) {
                binding.main.closeDrawer(binding.navView)
            } else {
                binding.main.openDrawer(binding.navView)
            }
        }

        binding.bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    updateScreenName("Good Day, $name")
                    replaceFragment(CustomerDashboardFragment())
                    true
                }
                R.id.stations -> {
                   replaceFragment(CustomerStationsFragment())
                    true
                }
                R.id.inventory -> {
                    replaceFragment(CustomerInventoryFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun updateScreenName(text: String) {
        binding.screenName.text = text
    }

    private fun openSettings() {
        val intent = Intent(this@CustomerDashboard, SettingsPage::class.java)
        val options = ActivityOptions.makeCustomAnimation(this@CustomerDashboard, R.anim.slide_in_left, R.anim.slide_out_right)
        startActivity(intent, options.toBundle())
    }

    private fun signOutUser() {
        // Sign out logic here

        val intent = Intent(this@CustomerDashboard, LoginPage::class.java)
        val options = ActivityOptions.makeCustomAnimation(this@CustomerDashboard, R.anim.slide_in_left, R.anim.slide_out_right)
        startActivity(intent, options.toBundle())
        finish()
    }
}