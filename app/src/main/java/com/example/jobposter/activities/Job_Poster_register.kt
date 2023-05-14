package com.example.jobposter.activities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.jobposter.Home
import com.example.jobposter.Notification
import com.example.jobposter.databinding.ActivityJobPosterRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Job_Poster_register : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityJobPosterRegisterBinding

    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    //progress
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobPosterRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //init progress dialog, will show while creating account
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

        //register button implementation
        binding.btnRegister.setOnClickListener{

            validateData()

        }
        binding.tvAccountLogin.setOnClickListener{

            startActivity(Intent(this@Job_Poster_register, JobPoster_login::class.java))
            finish()
        }

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

    private var name = ""
    private var password = ""
    private var email = ""
    private var phone = ""
    private fun validateData() {
        //Input data
        name = binding.etFullName.text.toString().trim()
        email = binding.etEmail.text.toString().trim()
        password = binding.etPassword.text.toString().trim()
        phone = binding.etPhone.text.toString().trim()

        //validation checks on data
        if (name.isEmpty()){
            Toast.makeText(this, "Enter your name to proceed", Toast.LENGTH_SHORT).show()
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Invalid Email Format", Toast.LENGTH_SHORT).show()
        }
        else if (password.isEmpty()){
            Toast.makeText(this, "Enter a password to proceed", Toast.LENGTH_SHORT).show()
        }
        else if(phone.isEmpty()){
            Toast.makeText(this, "Enter your phone number to proceed", Toast.LENGTH_SHORT).show()
        }
        else{
        //--create account
            createUserAccount()
        }


    }

    private fun createUserAccount() {
        //create account in firebase
        progressDialog.setMessage("Creating Account...")
        progressDialog.show()

        //creating a user in firebase auth
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                    //Account is created,add user data to db
                    setUpdateInfo()
                }
            .addOnFailureListener{
                progressDialog.dismiss()
                Toast.makeText(this, "Failed to create account", Toast.LENGTH_SHORT).show()
            }
    }

    private fun setUpdateInfo() {
        //save data into the firebase real time db

        progressDialog.setMessage("Saving user info")

        //getting time stamp of when it was saved
        val timestamp = System.currentTimeMillis()

        //getting current userid from auth
        val uid = firebaseAuth.uid

        //setup data to add in db
        val hashMap: HashMap<String, Any?> = HashMap()
        hashMap["uid"]=uid
        hashMap["email"]=email
        hashMap["name"]=name
        hashMap["profileImage"]=""
        hashMap["phone"]=phone
        hashMap["userType"]="jobposter"
        hashMap["timestamp"]=timestamp
        hashMap["password"]=password

        //set data to db
        val ref = FirebaseDatabase.getInstance("https://podiwadak-a4d20-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
        ref.child(uid!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Account created Successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, JobPoster_login::class.java))
                finish()
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(this, "Failed to create account due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

}