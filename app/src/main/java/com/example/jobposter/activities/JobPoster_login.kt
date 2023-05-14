package com.example.jobposter.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.jobposter.Home
import com.example.jobposter.Notification
import com.example.jobposter.databinding.ActivityJobPosterLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class JobPoster_login : AppCompatActivity() {
    //Hello World

    //view binding
    private lateinit var binding: ActivityJobPosterLoginBinding

    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    //progress dialog
    //private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobPosterLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

       //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener{
            validateData()
        }




        //navigate to register job_poster
        binding.tvCreateAccount.setOnClickListener{
            startActivity(Intent(this, Job_Poster_register::class.java))
        }

        //-----------nav bar implementation-------------------//
        //---------------bottom navbar implementation--------------//
        binding.btnHome.setOnClickListener{
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
        binding.btnNotification.setOnClickListener{
            val intent = Intent(this, Notification::class.java)
            startActivity(intent)
        }
        binding.btnJobs.setOnClickListener{
            val intent = Intent(this, Jobs_page::class.java)
            startActivity(intent)
        }
        binding.btnProfile.setOnClickListener{
            val intent = Intent(this, job_user_login::class.java)
            startActivity(intent)
        }
        binding.btnMore.setOnClickListener{
            val intent = Intent(this, activity_job_user_applyjob::class.java)
            startActivity(intent)
        }
    }
    //initializing variables to store data
    private var email=""
    private var password=""

    private fun validateData() {
        //getting data from login form
        email = binding.etFullName.text.toString().trim()
        password = binding.etPassword.text.toString().trim()

        //Validating data
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
        }
        else if (password.isEmpty()){
            Toast.makeText(this, "Enter a password to proceed", Toast.LENGTH_SHORT).show()
        }else{
            loginUser()
        }
    }

    private fun loginUser() {
        //login using firebase auth

       // progressDialog.setMessage("Logging in...")
        //progressDialog.show()

        //checking user
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener{
                //progressDialog.setMessage("Logged in")
                startActivity(Intent(this@JobPoster_login, JobPoster_profile::class.java))
                finish()
            }
            .addOnFailureListener {e->
                //progressDialog.dismiss()
                Toast.makeText(this, "Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@JobPoster_login, MainActivity::class.java))
            }
    }

    private fun checkUser() {
        //checking user and the user type

        //progressDialog.setMessage("Checking User...")

        val firebaseUser = firebaseAuth.currentUser!!
        val ref = FirebaseDatabase.getInstance("https://podiwadak-a4d20-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
        ref.child(firebaseUser.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                   //progressDialog.setMessage("Logged in")
                   startActivity(Intent(this@JobPoster_login, JobPoster_profile::class.java))
                    finish()
                }
                override fun onCancelled(error: DatabaseError) {
                    //progressDialog.dismiss()

                }
            })
    }
}