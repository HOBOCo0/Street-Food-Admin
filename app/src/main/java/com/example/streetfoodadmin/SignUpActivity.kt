package com.example.streetfoodadmin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.streetfoodadmin.databinding.ActivitySignUpBinding
import com.example.streetfoodadmin.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

/*
    This activity implements the sign up feature of the admin / to registers new user
 */
class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var passward: String
    private lateinit var nameOfRestaurant: String
    private lateinit var userName: String
    private lateinit var database: DatabaseReference

    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        // initialise firebase
        auth = Firebase.auth
        // initialise firebase realtime database
        database = Firebase.database.getReference()

        binding.createAccountButton.setOnClickListener {
            // taking user details in variables
            passward = binding.password.text.toString().trim()
            nameOfRestaurant = binding.restaurantName.text.toString().trim()
            userName = binding.userName.text.toString().trim()
            email = binding.emailAddress.text.toString().trim()

            // check is fields are blank
            if (userName.isBlank() || nameOfRestaurant.isBlank() || email.isBlank() || passward.isBlank()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
            } else {
                createAccount(email, passward)
            }

//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
        }

        // dummy data to choose location from
        val list = listOf("Jaipur", "Delhi", "Banglore", "Hyderabad", "Kolkata", "Mumbai", "Pune")

        // adapter to insert the data to dropdown list
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        binding.listOfLocation.setAdapter(adapter)

        binding.haveAccountButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun createAccount(email: String, passward: String) {
        auth.createUserWithEmailAndPassword(email, passward).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_LONG).show()
                saveUserData()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_LONG).show()
                Log.d("Account","createaccount: failure",task.exception)
            }
        }
    }

    // save data into database
    private fun saveUserData() {
        passward = binding.password.text.toString().trim()
        nameOfRestaurant = binding.restaurantName.text.toString().trim()
        userName = binding.userName.text.toString().trim()
        email = binding.emailAddress.text.toString().trim()

        val user = UserModel(userName,nameOfRestaurant,email,passward)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        //save user data Firebase database
        database.child("user").child(userId).setValue(user)
    }
}