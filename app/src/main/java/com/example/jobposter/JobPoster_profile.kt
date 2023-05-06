package com.example.jobposter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.bumptech.glide.Glide
import com.example.jobposter.databinding.ActivityJobPosterProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class JobPoster_profile : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityJobPosterProfileBinding
    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobPosterProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //loading user info into page
        firebaseAuth = FirebaseAuth.getInstance()
        loadUserInfo()

        //------edit account implementation
        binding.updateAccount.setOnClickListener{


        }


        //-----------nav bar implementation-------------------//
        binding.btnHome.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.btnNotification.setOnClickListener{
            val intent = Intent(this, Job_Poster_register::class.java)
            startActivity(intent)
        }
        binding.btnJobs.setOnClickListener{
            val intent = Intent(this, Job_page::class.java)
            startActivity(intent)
        }
        binding.btnProfile.setOnClickListener{
            val intent = Intent(this, JobPoster_profile::class.java)
            startActivity(intent)
        }
        binding.btnMore.setOnClickListener{
            val intent = Intent(this, Insert_Job::class.java)
            startActivity(intent)
        }

    }

    private fun loadUserInfo() {
        //db reference to get user info
        var ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseAuth.uid!!)
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                   //getting user info
                   val email = "${snapshot.child("email")}"
                   val name = "${snapshot.child("name")}"
                   val profileImage = "${snapshot.child("profileImage")}"
                   val phone = "${snapshot.child("phone")}"
                   val password = "${snapshot.child("password")}"
                   val uid = "${snapshot.child("uid")}"

                   //convert timestamp to proper date format
                   //val formattedDate = MyApplication.formatTimeStamp(timestamp.toLong())

                   //set image
                   //glide library to get images from firebase
                    try {
                        Glide.with(this@JobPoster_profile)
                            .load(profileImage)
                            .placeholder(R.drawable.ic_person)
                            .into(binding.ivProfilepic)

                    }catch (e: Exception){

                    }


                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
}