package com.cansal.aquaroute

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.cansal.aquaroute.databinding.ActivityOwnerDashboardBinding

class OwnerDashboard : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerDashboardBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOwnerDashboardBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.dashboardBG)
        toggle = ActionBarDrawerToggle(this, binding.main, R.string.open, R.string.close)
        binding.main.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val name = intent.getStringExtra("loggedInName")
        binding.screenName.text="Good Day, $name"
        replaceFragment(DeliveriesFragment())

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
                R.id.dashboard -> {
                    binding.screenName.text="Good Day, $name"
                    replaceFragment(DeliveriesFragment())
                    true
                }
                R.id.messages -> {
                    binding.screenName.text="Messages"
                    replaceFragment(ClientsFragment())
                    true
                }
                R.id.orders -> {
                    binding.screenName.text="Orders"
                    replaceFragment(OrdersOwnerFragment())
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

    private fun openSettings() {
        val intent = Intent(this@OwnerDashboard, SettingsPage::class.java)
        val options = ActivityOptions.makeCustomAnimation(this@OwnerDashboard, R.anim.slide_in_left, R.anim.slide_out_right)
        startActivity(intent, options.toBundle())
    }

    private fun signOutUser() {
        // Sign out logic here

        val intent = Intent(this@OwnerDashboard, LoginPage::class.java)
        val options = ActivityOptions.makeCustomAnimation(this@OwnerDashboard, R.anim.slide_in_left, R.anim.slide_out_right)
        startActivity(intent, options.toBundle())
        finish()
    }
}
