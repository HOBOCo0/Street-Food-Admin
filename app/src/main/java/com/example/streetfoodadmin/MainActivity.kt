package com.example.streetfoodadmin

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.streetfoodadmin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.addMenuCard.setOnClickListener {
            val intent = Intent(this,AddMenuActivity::class.java)
            startActivity(intent)
        }
        binding.allMenuCard.setOnClickListener {
            val intent = Intent(this,All_Items_Activity::class.java)
            startActivity(intent)
        }
    }
}