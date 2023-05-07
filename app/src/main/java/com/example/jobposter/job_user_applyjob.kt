package com.example.jobposter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class job_user_applyjob : AppCompatActivity() {
    private lateinit var EmpName: EditText
    private lateinit var EmpAge: EditText
    private lateinit var empNote :EditText
    private lateinit var EmpNumber : EditText
    private lateinit var EmpAdd : Button

    private lateinit var dbRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_user_applyjob)

        EmpName = findViewById(R.id.EmpName)
        EmpAge = findViewById(R.id.EmpAge)
        empNote = findViewById(R.id.empNote)
        EmpNumber = findViewById(R.id.EmpNumber)
        EmpAdd = findViewById(R.id.EmpAdd)

        //db connection
        dbRef = FirebaseDatabase.getInstance("https://podiwadak-a4d20-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("applyemployee")

        EmpAdd.setOnClickListener {
            saveapplyemployee()
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

//        notifyButton.setOnClickListener{
//            val intent = Intent(this, Notification::class.java)
//            startActivity(intent)
//        }

        jobsButton.setOnClickListener{
            val intent = Intent(this, Job_Poster_register::class.java)
            startActivity(intent)
        }

        profileButton.setOnClickListener{
            val intent = Intent(this, JobPoster_profile::class.java)
            startActivity(intent)
        }

        feedbackButton.setOnClickListener{
            val intent = Intent(this, Insert_Job::class.java)
            startActivity(intent)
        }
//        editNotificationButton.setOnClickListener{
//            val intent = Intent(this, NotificationEdit::class.java)
//            startActivity(intent)
//        }
    }
    //-------------------------------end toolbar---------------------------------------------
    //Add button function
    private fun saveapplyemployee() {
        //getting values
        val name = EmpName.text.toString()
        val age = EmpAge.text.toString()
        val description = empNote.text.toString()
        val phoneNumber = EmpNumber.text.toString()





        val id = dbRef.push().key!!

        val apply = applyjobDataClass(id,name,age,description,phoneNumber)

        dbRef.child(id!!).setValue(apply).addOnSuccessListener {
            Toast.makeText(this,"Successfully Added",Toast.LENGTH_SHORT).show()

            //clear input fileds
            EmpName.text.clear()
            EmpAge.text.clear()
            empNote.text.clear()
            EmpNumber.text.clear()


        }.addOnFailureListener {err->
            Toast.makeText(this,"Faied ${err.message}",Toast.LENGTH_SHORT).show()
        }
    }




}