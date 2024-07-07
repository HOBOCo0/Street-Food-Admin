package com.example.streetfoodadmin

import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.streetfoodadmin.databinding.ActivityAddMenuBinding
import com.example.streetfoodadmin.model.AllMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

/*
    Activity to add food items in the food menu app
 */
class AddMenuActivity : AppCompatActivity() {
    //food item detail
    private lateinit var foodName: String
    private lateinit var foodPrice: String
    private lateinit var foodDescription: String
    private var foodImageUri: Uri? = null
    private lateinit var foodIngrediants: String

    //firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private val binding: ActivityAddMenuBinding by lazy {
        ActivityAddMenuBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // init firebase and database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.addItemButton.setOnClickListener {
            //get data from fields
            foodName = binding.foodNameText.text.toString().trim()
            foodPrice = binding.foodPriceText.text.toString().trim()
            foodDescription = binding.DescriptionText.text.toString().trim()
            foodIngrediants = binding.ingredientsText.toString().trim()

            if (!(foodName.isBlank() || foodPrice.isBlank() || foodDescription.isBlank() || foodIngrediants.isBlank())) {
                uploadData()
                Toast.makeText(this, "Item Added successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
            }
        }
        binding.imageSelecter.setOnClickListener {
            pickImage.launch("image/*")
        }
//        binding.imageSelecter.setOnClickListener {
//            // to launch the image picker with a specific request to pick only images.
//            pickImage.launch(PickVisualMediaRequest((ActivityResultContracts.PickVisualMedia.ImageOnly)))
//        }

        // finish activity and move back to previous Activity
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun uploadData() {
        // get a reference to the "menu" node in the database
        val menuRef: DatabaseReference = database.getReference("menu")

        // Generate a unique key for each new menu
        val newItemKey = menuRef.push().key

        if (foodImageUri != null) {
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("menu_images/${newItemKey}.jpg")
            val uploadTask = imageRef.putFile(foodImageUri!!)

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnCompleteListener { downloadUrl ->
                    //
                    // Create a new menu item

                    val newItem = AllMenu(
                        foodName = foodName,
                        foodPrice = foodPrice,
                        foodDescription = foodDescription,
                        foodIngrediant = foodIngrediants,
                        foodImage = downloadUrl.toString()
                    )
                    newItemKey?.let { key ->
                        menuRef.child(key).setValue(newItem).addOnSuccessListener {
                            Toast.makeText(this, "Data upload successful", Toast.LENGTH_SHORT)
                                .show()
                        }.addOnFailureListener {
                            Toast.makeText(this, "Data upload Failed", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }

            }.addOnFailureListener {
                Toast.makeText(this, "Data upload Failed", Toast.LENGTH_SHORT).show()
            }

        } else {

            Toast.makeText(this, "Please Select an Image", Toast.LENGTH_SHORT).show()
        }
    }

    // to pick an image from the device's media gallery and display it in an ImageView.
    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            binding.selectedImage.setImageURI(uri)
            foodImageUri = uri
        }
    }
}