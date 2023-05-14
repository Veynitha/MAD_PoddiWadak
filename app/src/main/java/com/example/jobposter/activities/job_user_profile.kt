package com.example.jobposter.activities

import android.app.Notification
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.jobposter.R
import com.example.jobposter.databinding.ActivityJobUserProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
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
//        binding.updatebtn.setOnClickListener{
//            val intent = Intent(this, job_user_edit_profile::class.java)
//            startActivity(intent)
//        }
        //------edit account implementation
        binding.updatebtn.setOnClickListener{
            startActivity(Intent(this@job_user_profile, job_user_edit_profile::class.java))
            finish()
        }

        //------edit account implementation
        binding.userlogout.setOnClickListener{
            val intent = Intent(this, job_user_login::class.java)
            startActivity(intent)
        }
        //-----delete account implementation
        binding.userdeleteaccount.setOnClickListener{
            val user = firebaseAuth.currentUser
            user?.delete()?.addOnCompleteListener {
                if(it.isSuccessful){
                    //account is deleted
                    Toast.makeText(this,"Account was successfully deleted", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@job_user_profile, job_user_login::class.java))
                    finish()
                }
                else{
                    //account failed to delete
                    Toast.makeText(this,"Account failed to be deleted", Toast.LENGTH_SHORT).show()
                }
            }
        }


        //-----------------------------------toolbar button-----------------------------------------

        val homeButton = findViewById<Button>(R.id.btn_Home)
        val notifyButton = findViewById<Button>(R.id.btn_Notification)
        val jobsButton = findViewById<Button>(R.id.btn_Jobs)
        val profileButton = findViewById<Button>(R.id.btn_Profile)
        val feedbackButton = findViewById<Button>(R.id.btn_More)


        homeButton.setOnClickListener{
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

        notifyButton.setOnClickListener{
            val intent = Intent(this, Notification::class.java)
            startActivity(intent)
        }

        jobsButton.setOnClickListener{
            val intent = Intent(this, Jobs_page::class.java)
            startActivity(intent)
        }

        profileButton.setOnClickListener{
            val intent = Intent(this, job_user_profile::class.java)
            startActivity(intent)
        }

        feedbackButton.setOnClickListener{
            val intent = Intent(this, activity_job_user_applyjob::class.java)
            startActivity(intent)
        }

    }




    private fun loadUserInfo() {
        //db reference to get user info
        var ref = FirebaseDatabase.getInstance("https://podiwadak-a4d20-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("jobseeker")
        ref.child(firebaseAuth.uid!!)
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    //getting user info
                    val name = "${snapshot.child("name").value}"
                    val location = "${snapshot.child("location").value}"
                    val profileImage = "${snapshot.child("profileImage").value}"
                    val phone = "${snapshot.child("phone").value}"
                    val email = "${snapshot.child("email").value}"
                    //val password = "${snapshot.child("password").value}"
                    val uid = "${snapshot.child("uid").value}"

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