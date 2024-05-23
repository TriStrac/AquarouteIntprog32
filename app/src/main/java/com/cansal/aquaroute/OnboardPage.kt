package com.cansal.aquaroute

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.animation.Animation
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import android.view.animation.AnimationUtils
import com.cansal.aquaroute.adapters.OnboardingViewPagerAdapter
import com.cansal.aquaroute.databinding.ActivityOnboardPageBinding

class OnboardPage : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardPageBinding
    private lateinit var viewPagerAdapter: OnboardingViewPagerAdapter
    private lateinit var dots: Array<TextView>
    private lateinit var fadeInAnimation: Animation
    private lateinit var fadeOutAnimation: Animation
    private var lastPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        fadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.skipButton.setOnClickListener {
            binding.onBoardViewPager.setCurrentItem(viewPagerAdapter.itemCount - 1, true)
        }

        binding.nextFab.setOnClickListener {
            if (getItem(0) < 2) {
                binding.onBoardViewPager.setCurrentItem(getItem(1), true)
            }
        }

        binding.loginAccount.setOnClickListener {
            val intent = Intent(this@OnboardPage, LoginPage::class.java)
            startActivity(intent)
            finish() // Optional: Finish the current activity after starting the new one
        }

        binding.createAccount.setOnClickListener {
            val intent = Intent(this@OnboardPage, CreateAccountPage::class.java)
            startActivity(intent)
            finish() // Optional: Finish the current activity after starting the new one
        }


        viewPagerAdapter = OnboardingViewPagerAdapter(this)
        binding.onBoardViewPager.adapter = viewPagerAdapter

        setUpIndicator(0)
        binding.onBoardViewPager.registerOnPageChangeCallback(viewListener)
    }

    private fun setUpIndicator(position: Int) {
        dots = Array(3) { TextView(this) }
        binding.dotIndicator.removeAllViews()

        for (i in dots.indices) {
            dots[i] = TextView(this).apply {
                text = Html.fromHtml("&#8226", Html.FROM_HTML_MODE_LEGACY)
                textSize = 35f
                setTextColor(resources.getColor(R.color.inactive, applicationContext.theme))
            }
            binding.dotIndicator.addView(dots[i])
        }

        if (dots.isNotEmpty()) {
            dots[position].setTextColor(resources.getColor(R.color.active, applicationContext.theme))
        }

        if (position == 2 || lastPosition == 2 && position == 3) {
            if (position == 2) {
                binding.skipButton.startAnimation(fadeOutAnimation)
                binding.nextFab.startAnimation(fadeOutAnimation)
                binding.dotIndicator.startAnimation(fadeOutAnimation)
                binding.createAccount.startAnimation(fadeInAnimation)
                binding.loginAccount.startAnimation(fadeInAnimation)

                binding.skipButton.visibility = View.INVISIBLE
                binding.nextFab.visibility = View.INVISIBLE
                binding.dotIndicator.visibility = View.INVISIBLE
                binding.createAccount.visibility = View.VISIBLE
                binding.loginAccount.visibility = View.VISIBLE
            } else {
                binding.skipButton.startAnimation(fadeInAnimation)
                binding.nextFab.startAnimation(fadeInAnimation)
                binding.dotIndicator.startAnimation(fadeInAnimation)
                binding.createAccount.startAnimation(fadeOutAnimation)
                binding.loginAccount.startAnimation(fadeOutAnimation)

                binding.skipButton.visibility = View.VISIBLE
                binding.nextFab.visibility = View.VISIBLE
                binding.dotIndicator.visibility = View.VISIBLE
                binding.createAccount.visibility = View.INVISIBLE
                binding.loginAccount.visibility = View.INVISIBLE
            }
        } else {
            binding.skipButton.visibility = View.VISIBLE
            binding.nextFab.visibility = View.VISIBLE
            binding.dotIndicator.visibility = View.VISIBLE
            binding.createAccount.visibility = View.INVISIBLE
            binding.loginAccount.visibility = View.INVISIBLE
        }
        lastPosition = position
    }

    private val viewListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            setUpIndicator(position)
        }
    }

    private fun getItem(i: Int): Int {
        return binding.onBoardViewPager.currentItem + i
    }
}
