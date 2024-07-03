package com.example.streetfoodadmin

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.streetfoodadmin.databinding.ActivitySignUpBinding
/*
    This activity implements the sign up feature of the admin / to registers new user
 */
class SignUpActivity : AppCompatActivity() {
    private val binding:ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        // dummy data to choose location from
        val list = listOf("Jaipur","Delhi","Banglore","Hyderabad","Kolkata","Mumbai","Pune")

        // adapter to insert the data to dropdown list
        val adapter =ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        binding.listOfLocation.setAdapter(adapter)

        binding.haveAccountButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}