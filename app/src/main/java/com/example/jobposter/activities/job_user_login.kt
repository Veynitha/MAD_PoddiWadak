package com.example.jobposter.activities

import android.app.Notification
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import com.example.jobposter.Home
import com.example.jobposter.R
import com.example.jobposter.databinding.ActivityJobUserLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class job_user_login : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityJobUserLoginBinding
    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobUserLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener{
            validateData()
        }
        //navigate to register job_user
                binding.tvCreateAccount.setOnClickListener{
                    startActivity(Intent(this, job_user_register::class.java))

                }
        //navigate to jobposter_register

        binding.tvjobLister.setOnClickListener {
            startActivity(Intent(this, Job_Poster_register::class.java))
        }

        val homeButton = findViewById<Button>(R.id.btn_Home)
        val notifyButton = findViewById<Button>(R.id.btn_Notification)
        val jobsButton = findViewById<Button>(R.id.btn_Jobs)
        val profileButton = findViewById<Button>(R.id.btn_Profile)
        val feedbackButton = findViewById<Button>(R.id.btn_More)
        val putNotificationButton = findViewById<Button>(R.id.putNotification)

        //---------------bottom navbar implementation--------------//
        binding.btnHome.setOnClickListener{
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
        binding.btnNotification.setOnClickListener{
            val intent = Intent(this, com.example.jobposter.Notification::class.java)
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
        //checking user
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener{
                //progressDialog.setMessage("Logged in")
                startActivity(Intent(this@job_user_login, job_user_profile::class.java))
                finish()
            }
            .addOnFailureListener {e->
                //progressDialog.dismiss()
                Toast.makeText(this, "Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@job_user_login, job_user_register::class.java))
            }
    }

    private fun checkUser() {
        //checking user and the user type

        //progressDialog.setMessage("Checking User...")

        val firebaseUser = firebaseAuth.currentUser!!
        val ref = FirebaseDatabase.getInstance("https://podiwadak-a4d20-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("jobseeker")
        ref.child(firebaseUser.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //progressDialog.setMessage("Logged in")
                    startActivity(Intent(this@job_user_login, job_user_profile::class.java))
                    finish()
                }
                override fun onCancelled(error: DatabaseError) {
                    //progressDialog.dismiss()

                }
            })
    }
}