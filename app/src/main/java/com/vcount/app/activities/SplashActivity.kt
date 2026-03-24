package com.vcount.app.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.vcount.app.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val title = findViewById<TextView>(R.id.title)
        val subtitle = findViewById<TextView>(R.id.subtitle)
        val ring = findViewById<View>(R.id.ring)

        // Load animations safely
        val pulse: Animation = AnimationUtils.loadAnimation(this, R.anim.pulse_ring)
        val fadeIn: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        // Start ring animation
        ring.startAnimation(pulse)

        // Initially invisible
        title.alpha = 0f
        subtitle.alpha = 0f

        // Fade title
        Handler(Looper.getMainLooper()).postDelayed({
            title.startAnimation(fadeIn)
            title.alpha = 1f
        }, 500)

        // Fade subtitle
        Handler(Looper.getMainLooper()).postDelayed({
            subtitle.startAnimation(fadeIn)
            subtitle.alpha = 1f
        }, 1000)

        // Move to Home
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }, 2500)
    }
}