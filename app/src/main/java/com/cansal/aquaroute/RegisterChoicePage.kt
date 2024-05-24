package com.cansal.aquaroute

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.cansal.aquaroute.adapters.RegisterChoiceViewPagerAdapter
import com.cansal.aquaroute.databinding.ActivityRegisterChoicePageBinding

class RegisterChoicePage : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterChoicePageBinding
    private lateinit var dots: Array<TextView>
    private lateinit var fadeInAnimation: Animation
    private lateinit var fadeOutAnimation: Animation
    private var lastPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterChoicePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpAnimations()
        setUpViewPager()
        binding.nextChoiceButton.setOnClickListener {
            binding.choicePageViewer.setCurrentItem(binding.choicePageViewer.currentItem + 1, true)
        }
        binding.backChoiceButton.setOnClickListener {
            binding.choicePageViewer.setCurrentItem(binding.choicePageViewer.currentItem - 1, true)
        }
        binding.changeOptionButton.setOnClickListener {
            if (binding.choicePageViewer.currentItem < 1) {
                binding.choicePageViewer.setCurrentItem(binding.choicePageViewer.currentItem + 1, true)
            }else if (binding.choicePageViewer.currentItem > 0) {
            binding.choicePageViewer.setCurrentItem(binding.choicePageViewer.currentItem - 1, true)
            }
        }
        binding.proceedRegisterChoice.setOnClickListener {
            if (binding.choicePageViewer.currentItem < 1) {

            }else if (binding.choicePageViewer.currentItem > 0) {
                val intent = Intent(this@RegisterChoicePage, OwnerRegistrationPage::class.java).apply {
                    putExtra("targetPage", 2)
                }
                val options = ActivityOptions.makeCustomAnimation(this@RegisterChoicePage, R.anim.slide_in_right, R.anim.slide_out_left)
                startActivity(intent, options.toBundle())
                finish()
            }
        }
        binding.backButtonChoices.setOnClickListener {
            val intent = Intent(this@RegisterChoicePage, LoginPage::class.java).apply {
                putExtra("targetPage", 2)
            }
            val options = ActivityOptions.makeCustomAnimation(this@RegisterChoicePage, R.anim.slide_in_left, R.anim.slide_out_right)
            startActivity(intent, options.toBundle())
            finish()
        }
    }

    private fun setUpAnimations() {
        fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        fadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out)
    }

    private fun setUpViewPager() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.choicePageViewer.adapter = RegisterChoiceViewPagerAdapter(this)
        binding.choicePageViewer.registerOnPageChangeCallback(viewListener)
    }

    private fun setUpIndicator(position: Int) {
        dots = Array(2) { TextView(this) }
        binding.dotIndicator2.removeAllViews()

        for (i in dots.indices) {
            dots[i] = TextView(this).apply {
                text = Html.fromHtml("&#8226", Html.FROM_HTML_MODE_LEGACY)
                textSize = 35f
                setTextColor(resources.getColor(R.color.inactive, applicationContext.theme))
            }
            binding.dotIndicator2.addView(dots[i])
        }

        if (dots.isNotEmpty()) {
            dots[position].setTextColor(resources.getColor(R.color.active, applicationContext.theme))
        }

        if (position == 1) {
            binding.changeOptionButton.apply {
                startAnimation(fadeOutAnimation)
                text = getString(R.string.i_am_station_manager)
                startAnimation(fadeInAnimation)
            }
            binding.nextChoiceButton.apply {
                startAnimation(fadeOutAnimation)
                visibility = View.INVISIBLE
            }
            binding.backChoiceButton.apply {
                startAnimation(fadeInAnimation)
                visibility = View.VISIBLE
            }
        } else {
            binding.changeOptionButton.apply {
                startAnimation(fadeOutAnimation)
                text = getString(R.string.i_am_customer)
                startAnimation(fadeInAnimation)
            }
            binding.nextChoiceButton.apply {
                startAnimation(fadeInAnimation)
                visibility = View.VISIBLE
            }
            binding.backChoiceButton.apply {
                startAnimation(fadeOutAnimation)
                visibility = View.INVISIBLE
            }
        }

        lastPosition = position
    }

    private val viewListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            setUpIndicator(position)
        }
    }
}
