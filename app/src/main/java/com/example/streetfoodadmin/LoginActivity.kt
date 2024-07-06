package com.example.streetfoodadmin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.streetfoodadmin.databinding.ActivityLoginBinding
import com.example.streetfoodadmin.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

/*
 This activity implements the login feature / sign in user
  */

class LoginActivity : AppCompatActivity() {

    private var nameOfRestaurant: String? = null
    private var userName: String? = null
    private lateinit var email: String
    private lateinit var passward: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient


    private val TAG = "Login Activity"

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client)).requestEmail().build()
        // initialise firebase
        auth = Firebase.auth
        // initialise database
        database = Firebase.database.reference
        // google SigneIn
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        binding.loginButton.setOnClickListener {

            // get text from edit text
            email = binding.editTextEmailAddress.text.toString().trim()
            passward = binding.editTextPassword.text.toString().trim()
            // check if field is empty or not
            if (email.isBlank() || passward.isBlank()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_LONG).show()
            } else {
                createUserAccount(email, passward)
            }
        }
        binding.signInGoogleButton.setOnClickListener {
            val signIntent = googleSignInClient.signInIntent
            launcher.launch(signIntent)
        }
        binding.DontHvAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // if user has account then it will sign in, if not then account will be created and user will log in automatically
    private fun createUserAccount(email: String, passward: String) {
        auth.signInWithEmailAndPassword(email, passward).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                Toast.makeText(this, "Login successfull", Toast.LENGTH_SHORT).show()
                updateUi(user)
            } else {
                auth.createUserWithEmailAndPassword(email, passward).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Toast.makeText(
                            this,
                            "User Created And Login Successfull",
                            Toast.LENGTH_SHORT
                        ).show()
                        saveUserData()
                        updateUi(user)
                    } else {
                        Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "createUserAccount:Authentication Failed", task.exception)
                    }
                }
            }
        }
    }

    private fun saveUserData() {
        email = binding.editTextEmailAddress.text.toString().trim()
        passward = binding.editTextPassword.text.toString().trim()

        val user = UserModel(userName, nameOfRestaurant, email, passward)
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId?.let {
            database.child("user").child(it).setValue(user)
        }
    }

// launcher for google signIn
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result->
        if (result.resultCode == Activity.RESULT_OK){
           val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if (task.isSuccessful){
                val account: GoogleSignInAccount = task.result
                val credentials = GoogleAuthProvider.getCredential(account.idToken,null)
                auth.signInWithCredential(credentials).addOnCompleteListener { authTask->
                    if(authTask.isSuccessful){
                        // successful sign in with google
                        Toast.makeText(this,"Successfully sign-in with google",Toast.LENGTH_SHORT).show()
                        updateUi(authTask.result?.user)
                    }else{
                        Toast.makeText(this,"Google Sign-in Failed",Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this,"Google Sign-in Failed",Toast.LENGTH_SHORT).show()
            }
        }
    }
    // check if user is already login
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser!=null){
            startActivity((Intent(this,MainActivity::class.java)))
            finish()
        }
    }
    private fun updateUi(user: FirebaseUser?) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}