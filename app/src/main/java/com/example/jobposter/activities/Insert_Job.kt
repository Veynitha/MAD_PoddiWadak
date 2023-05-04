package com.example.jobposter.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jobposter.models.JobModel
import com.example.jobposter.databinding.ActivityInsertJobBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Insert_Job : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityInsertJobBinding

    //db reference
    private lateinit var dbRef: DatabaseReference

    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        //creating reference
        dbRef = FirebaseDatabase.getInstance("https://podiwadak-a4d20-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Jobs")

        //--------insert job button--------//
        binding.btnInsertJob.setOnClickListener {
            saveJobDetails()
        }

        //---------------bottom navbar implementation--------------//
        binding.btnHome.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.btnNotification.setOnClickListener{
            val intent = Intent(this, Job_Poster_register::class.java)
            startActivity(intent)
        }
        binding.btnJobs.setOnClickListener{
            val intent = Intent(this, Jobs_page::class.java)
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

    private fun saveJobDetails() {
        val jobTitle = binding.etJobTitle.text.toString()
        val jobType = binding.etJobType.text.toString()
        val payment = binding.etJobPayment.text.toString()
        val description = binding.etJobDescription.text.toString()
        val location = binding.etLocation.text.toString()
        val contactNo = binding.etJobPhone.text.toString()
        //validations for fields
        if(jobTitle.isEmpty()){
            binding.etJobTitle.error = "Please enter name"
        }
        if (jobType.isEmpty()){
            binding.etJobType.error = "Please enter type"
        }
        if (payment.isEmpty()){
            binding.etJobPayment.error = "Please enter Payment"
        }
        if (description.isEmpty()){
            binding.etJobDescription.error = "Please enter Description"
        }
        if (location.isEmpty()){
            binding.etLocation.error = "Please enter Location"
        }
        if (contactNo.isEmpty()){
            binding.etJobPhone.error = "Please enter Location"
        }
        //setting key and object
        val jobId = dbRef.push().key!!

        val job = JobModel(jobId, jobTitle, jobType, payment, description, location, contactNo)
        //sending data to database
        dbRef = FirebaseDatabase.getInstance("https://podiwadak-a4d20-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Jobs")
        dbRef.child(jobId!!).setValue(job)
            .addOnSuccessListener {
                binding.etJobTitle.setText("")
                binding.etJobType.setText("")
                binding.etLocation.setText("")
                binding.etJobPayment.setText("")
                binding.etJobPhone.setText("")
                binding.etJobDescription.setText("")
                Toast.makeText(this,"Data inserted Successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {err->
                Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_SHORT).show()
            }

    }
}