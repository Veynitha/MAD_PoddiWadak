package com.example.jobposter

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.jobposter.databinding.ActivityJobPosterProfileBinding
import com.example.jobposter.databinding.ActivityJobUserProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.R
import java.lang.Exception

class job_user_profile : AppCompatActivity() {
    private lateinit var database: DatabaseReference


    //view binding
    private lateinit var binding: ActivityJobUserProfileBinding
    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //loading user info into page
        firebaseAuth = FirebaseAuth.getInstance()
        loadUserInfo()

        //------edit account implementation
        binding.updatebtn.setOnClickListener{
            val intent = Intent(this, job_user_edit_profile::class.java)
            startActivity(intent)



        }
        //------edit account implementation
        binding.userlogout.setOnClickListener{
            val intent = Intent(this, job_user_login::class.java)
            startActivity(intent)



        }
        //-----------nav bar implementation-------------------//
        binding.btnHome.setOnClickListener{
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
        binding.btnNotification.setOnClickListener{
            val intent = Intent(this, job_user_login::class.java)
            startActivity(intent)
        }
        binding.btnJobs.setOnClickListener{
            val intent = Intent(this, Job_page::class.java)
            startActivity(intent)
        }
        binding.btnProfile.setOnClickListener{
            val intent = Intent(this, job_user_profile::class.java)
            startActivity(intent)
        }
        binding.btnMore.setOnClickListener{
            val intent = Intent(this, Insert_Job::class.java)
            startActivity(intent)
        }

    }




    private fun loadUserInfo() {
        //db reference to get user info
        var ref = FirebaseDatabase.getInstance("https://podiwadak-a4d20-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
        ref.child(firebaseAuth.uid!!)
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    //getting user info
                    val name = "${snapshot.child("name").value}"
                    val location = "${snapshot.child("location").value}"
                    val profileImage = "${snapshot.child("profileImage").value}"
                    val phone = "${snapshot.child("phone").value}"
                    val email = "${snapshot.child("email").value}"
                    val password = "${snapshot.child("password").value}"
                    val eId = "${snapshot.child("uid").value}"

                    binding.eName.text = name
                    binding.EmNumber.text=phone
                    binding.EmEmail.text=email
                    binding.EmLocation.text=location



                    //convert timestamp to proper date format
                    //val formattedDate = MyApplication.formatTimeStamp(timestamp.toLong())

                    //set image
                    //glide library to get images from firebase
                    try {
                        Glide.with(this@job_user_profile)
                            .load(profileImage)
                            .placeholder(com.example.jobposter.R.drawable.ic_person)
                            .into(binding.myProfilepic)

                    }catch (e: Exception){

                    }


                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
}