package com.example.streetfoodadmin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.streetfoodadmin.databinding.ActivityAdminProfileBinding

class AdminProfile : AppCompatActivity() {

    private val binding: ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }
        binding.apply {
            nameTextView.isEnabled = false
            addressTextView.isEnabled = false
            emailTextView.isEnabled = false
            phoneNoTextView.isEnabled = false
            passwardTextView.isEnabled = false
        }
        var isEnable = false
        binding.editProfileText.setOnClickListener {
            isEnable =!isEnable

            binding.apply {
                nameTextView.isEnabled = isEnable
                addressTextView.isEnabled = isEnable
                emailTextView.isEnabled = isEnable
                phoneNoTextView.isEnabled = isEnable
                passwardTextView.isEnabled = isEnable
            }

            if(isEnable){
                binding.nameTextView.requestFocus()
            }
        }

    }
}