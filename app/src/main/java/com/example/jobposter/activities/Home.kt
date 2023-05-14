package com.example.jobposter.activities

import android.app.Notification
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.jobposter.R

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val homeButton = findViewById<Button>(R.id.btn_Home)
        val notifyButton = findViewById<Button>(R.id.btn_Notification)
        val jobsButton = findViewById<Button>(R.id.btn_Jobs)
        val profileButton = findViewById<Button>(R.id.btn_Profile)
        val feedbackButton = findViewById<Button>(R.id.btn_More)
        val putNotificationButton = findViewById<Button>(R.id.putNotification)

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
            val intent = Intent(this, job_user_login::class.java)
            startActivity(intent)
        }

        feedbackButton.setOnClickListener{
            val intent = Intent(this, activity_job_user_applyjob::class.java)
            startActivity(intent)
        }
//        putNotificationButton.setOnClickListener{
//            val intent = Intent(this, NotificationAdd::class.java)
//            startActivity(intent)
//
//        }
    }
}