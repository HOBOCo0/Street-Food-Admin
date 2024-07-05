package com.example.streetfoodadmin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        binding.dispatchOrderCard.setOnClickListener {
            val intent = Intent(this,OutForDeliveryActivity::class.java)
            startActivity(intent)
        }
        binding.adminProfileCard.setOnClickListener {
            val intent = Intent(this,AdminProfile::class.java)
            startActivity(intent)
        }
        binding.createNewUserCard.setOnClickListener {
            val intent = Intent(this,CreateNewUserActivity::class.java)
            startActivity(intent)
        }
        binding.pendingOrderLinearLayout.setOnClickListener {
            val intent = Intent(this,PendingOrderActivity::class.java)
            startActivity(intent)
        }
    }
}