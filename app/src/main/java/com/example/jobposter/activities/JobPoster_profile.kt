package com.example.jobposter.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.jobposter.Home
import com.example.jobposter.Notification
import com.example.jobposter.R
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
            startActivity(Intent(this@JobPoster_profile, Job_Poster_editProfile::class.java))
            finish()
        }

        //-----delete account implementation
        binding.btnDeleteUser.setOnClickListener{
            val user = firebaseAuth.currentUser
            user?.delete()?.addOnCompleteListener {
                if(it.isSuccessful){
                    //account is deleted
                    Toast.makeText(this,"Account was successfully deleted", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@JobPoster_profile, JobPoster_login::class.java))
                    finish()
                }
                else{
                    //account failed to delete
                    Toast.makeText(this,"Account failed to be deleted", Toast.LENGTH_SHORT).show()
                }
            }
        }

        //--------add job navigation implementation----------//
        binding.btnAddJob.setOnClickListener {
            startActivity(Intent(this, Insert_Job::class.java))
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

    private fun loadUserInfo() {
        //db reference to get user info
        var ref = FirebaseDatabase.getInstance("https://podiwadak-a4d20-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
        ref.child(firebaseAuth.uid!!)
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                   //getting user info
                   var email = "${snapshot.child("email").value}"
                   var name = "${snapshot.child("name").value}"
                   val profileImage = "${snapshot.child("profileImage").value}"
                   val phone = "${snapshot.child("phone").value}"
                   val password = "${snapshot.child("password").value}"
                   val uid = "${snapshot.child("uid").value}"

                   //convert timestamp to proper date format


                    //set data to page
                    binding.tvFullName.text = name
                    binding.tvEmail.text = email
                    binding.tvPhone.text = phone


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