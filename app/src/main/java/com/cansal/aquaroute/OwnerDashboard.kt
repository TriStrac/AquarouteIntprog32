package com.cansal.aquaroute

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.cansal.aquaroute.databinding.ActivityOwnerDashboardBinding
import com.cansal.aquaroute.storage.LocalStorage

class OwnerDashboard : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerDashboardBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var localStorage: LocalStorage
    private var state = 1
    private val PROFILE_UPDATE_REQUEST_CODE = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOwnerDashboardBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        localStorage=LocalStorage(this)

        window.statusBarColor = ContextCompat.getColor(this, R.color.dashboardBG)
        toggle = ActionBarDrawerToggle(this, binding.main, R.string.open, R.string.close)
        binding.main.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val name = intent.getStringExtra("loggedInName")
        binding.screenName.text="Good Day, $name"
        replaceFragment(OwnerHomeDashboardFragment())

        val navHeaderView = binding.navView.getHeaderView(0)
        val sideBarName: TextView = navHeaderView.findViewById(R.id.sideBarName)
        val sideBarEmail: TextView = navHeaderView.findViewById(R.id.sideBarEmail)

        sideBarName.text = localStorage.loggedInName
        sideBarEmail.text = localStorage.loggedInEmail

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

        binding.multiIconTopRight.setOnClickListener {
            when(state){
                1 ->{

                    true
                }
                2 ->{

                    true
                }
                3 ->{
                    openHistory()
                    true
                }
                else -> false
            }
        }


        binding.bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.dashboard -> {
                    state=1
                    stateCheck()
                    updateScreenName("Good Day, $name")
                    replaceFragment(OwnerHomeDashboardFragment())
                    true
                }
                R.id.messages -> {
                    state=2
                    stateCheck()
                    updateScreenName("Messages")
                    replaceFragment(ClientsFragment())
                    true
                }
                R.id.orders -> {
                    state=3
                    stateCheck()
                    replaceFragment(OrdersOwnerFragment())
                    true
                }
                else -> false
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PROFILE_UPDATE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Reload the sidebar email and name from the updated LocalStorage
            val navHeaderView = binding.navView.getHeaderView(0)
            val sideBarName: TextView = navHeaderView.findViewById(R.id.sideBarName)
            val sideBarEmail: TextView = navHeaderView.findViewById(R.id.sideBarEmail)

            sideBarName.text = localStorage.loggedInName
            sideBarEmail.text = localStorage.loggedInEmail
        }
    }

    private fun stateCheck(){
        when(state){
            1 ->{
                updateMultiIcon(R.drawable.outline_notifications_24)
                true
            }
            2 ->{
                updateMultiIcon(R.drawable.baseline_search_24)
                true
            }
            3 ->{

            }
        }
    }


    fun updateScreenName(text: String) {
        binding.screenName.text = text
    }

    fun updateMultiIcon(icon:Int){
        binding.multiIconTopRight.setImageResource(icon)
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

    private fun openHistory(){
        val intent = Intent(this@OwnerDashboard, OwnerHistoryPage::class.java)
        val options = ActivityOptions.makeCustomAnimation(this@OwnerDashboard, R.anim.slide_in_right, R.anim.slide_out_left)
        startActivity(intent, options.toBundle())
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
