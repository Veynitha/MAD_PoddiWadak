package com.example.jobposter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.jobposter.databinding.ActivityJobUserApplyjobBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class job_user_applyjob : AppCompatActivity() {
    //view binding
    private lateinit var binding: ActivityJobUserApplyjobBinding

    //db reference
    private lateinit var dbRef: DatabaseReference

    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobUserApplyjobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        //creating reference
                dbRef = FirebaseDatabase.getInstance("https://podiwadak-a4d20-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Employee")

        //--------insert userinfo button--------//
        binding.EmpAdd.setOnClickListener {
            val intent = Intent(this, job_user_myjob::class.java)
            startActivity(intent)
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
            val intent = Intent(this, job_user_myjob::class.java)
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

     private fun saveEmployeeData(){
//         val EmpName=EmpName.text.toString()
//         val EmpAge=EmpAge.text.toString()
//         val EmpNote=EmpNote.text.toString()
//         val EmpNumber=EmpNumber.text.toString()
//         //val EmpAdd=EmpAdd.text.toString()

         val name = binding.EmpName.text.toString()
         val age = binding.EmpAge.text.toString()
         val note = binding.empNote.text.toString()
         val pnumber = binding.EmpNumber.text.toString()

         //validations for fields
         if(name.isEmpty()){
             binding.EmpName.error = "Please enter name"
         }
         if (age.isEmpty()){
             binding.EmpAge.error = "Please enter Age"
         }
         if (note.isEmpty()){
             binding.empNote.error = "Please enter Note"
         }
         if (pnumber.isEmpty()){
             binding.EmpNumber.error = "Please enter Number"
         }

         //setting key and object
         val uId = dbRef.push().key!!

         val Employee = employeeModel(uId, name, age, note, pnumber)
         //sending data to database
         dbRef = FirebaseDatabase.getInstance("https://podiwadak-a4d20-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Employee")
         dbRef.child(uId!!).setValue(Employee)
             .addOnSuccessListener {
                 binding.EmpName.setText("")
                 binding.EmpAge.setText("")
                 binding.empNote.setText("")
                 binding.EmpNumber.setText("")

                 Toast.makeText(this,"Data inserted Successfully", Toast.LENGTH_SHORT).show()
             }.addOnFailureListener {err->
                 Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_SHORT).show()
             }

     }

    private fun employeeModel(
         uId: String? = null,
       // uId: String,
        name: String? = null,
        age: String? = null,
        note: String? = null,
        pnumber: String? = null
    ): Any? {
        TODO("Not yet implemented")
    }
}