package com.example.streetfoodadmin

import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.streetfoodadmin.databinding.ActivityAddMenuBinding
/*
    Activity to add food items in the food menu app
 */
class AddMenuActivity : AppCompatActivity() {
    private val binding: ActivityAddMenuBinding by lazy {
        ActivityAddMenuBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.imageSelecter.setOnClickListener {
            // to launch the image picker with a specific request to pick only images.
            pickImage.launch(PickVisualMediaRequest((ActivityResultContracts.PickVisualMedia.ImageOnly)))
        }
          // finish activity and move back to previous Activity
        binding.backButton.setOnClickListener {
            finish()
        }
    }
    // to pick an image from the device's media gallery and display it in an ImageView.
    val pickImage = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                binding.selectedImage.setImageURI(uri)
            }
    }
}