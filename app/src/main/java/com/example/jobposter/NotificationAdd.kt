package com.example.jobposter

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jobposter.activities.Insert_Job
import com.example.jobposter.activities.JobPoster_login
import com.example.jobposter.activities.Jobs_page
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class NotificationAdd: AppCompatActivity() {
    //Notification insert part
    private lateinit var inputName: EditText
    private lateinit var inputEmail : EditText
    private lateinit var inputPhoneNumber :EditText
    private lateinit var inputJobTitel : EditText
    private lateinit var inputNotification : EditText
    private lateinit var btnAdd : Button

    private lateinit var dbRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_add)

        inputName = findViewById(R.id.inputName)
        inputEmail = findViewById(R.id.inputEmail)
        inputPhoneNumber = findViewById(R.id.inputPhoneNumber)
        inputJobTitel = findViewById(R.id.inputJobTitel)
        inputNotification = findViewById(R.id.inputNotification)
        btnAdd = findViewById(R.id.btnAdd)

        //db connection
        dbRef = FirebaseDatabase.getInstance("https://podiwadak-a4d20-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Notifications")

        btnAdd.setOnClickListener {
            saveNotifications()
        }
//-----------------------------------toolbar button-----------------------------------------

        val homeButton = findViewById<Button>(R.id.btn_Home)
        val notifyButton = findViewById<Button>(R.id.btn_Notification)
        val jobsButton = findViewById<Button>(R.id.btn_Jobs)
        val profileButton = findViewById<Button>(R.id.btn_Profile)
        val feedbackButton = findViewById<Button>(R.id.btn_More)
        val editNotificationButton = findViewById<Button>(R.id.but_edit)

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
            val intent = Intent(this, JobPoster_login::class.java)
            startActivity(intent)
        }

        feedbackButton.setOnClickListener{
            val intent = Intent(this, Insert_Job::class.java)
            startActivity(intent)
        }
        editNotificationButton.setOnClickListener{
            val intent = Intent(this, NotificationEdit::class.java)
            startActivity(intent)
        }
    }
//-------------------------------end toolbar---------------------------------------------
    //Add button function
    private fun saveNotifications() {
        //getting values
        val name = inputName.text.toString()
        val email = inputEmail.text.toString()
        val phoneNumber = inputPhoneNumber.text.toString()
        val jobTitel = inputJobTitel.text.toString()
        val notification =inputNotification.text.toString()



        val id = dbRef.push().key!!

        val notifi = NotificationDataClass(id,name,email,phoneNumber,jobTitel,notification)

        dbRef.child(id!!).setValue(notifi).addOnSuccessListener {
            Toast.makeText(this,"Successfully Added",Toast.LENGTH_SHORT).show()

            //clear input fileds
            inputName.text.clear()
            inputEmail.text.clear()
            inputPhoneNumber.text.clear()
            inputJobTitel.text.clear()
            inputNotification.text.clear()

        }.addOnFailureListener {err->
            Toast.makeText(this,"Faied ${err.message}",Toast.LENGTH_SHORT).show()
        }
    }
}