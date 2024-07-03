package com.example.streetfoodadmin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.streetfoodadmin.databinding.ActivitySplashScreenBinding

/*
    This activity implements the splash screen of the application
 */
class SplashScreenActivity : AppCompatActivity() {
    private val binding:ActivitySplashScreenBinding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // using handler and looper to hold the splash screen for 3 sec before moving to login activity.
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        },3000)
    }
}