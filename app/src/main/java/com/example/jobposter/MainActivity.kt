package com.example.jobposter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.jobposter.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeButton = findViewById<Button>(R.id.btn_Home)
        val notifyButton = findViewById<Button>(R.id.btn_Notification)
        val jobsButton = findViewById<Button>(R.id.btn_Jobs)
        val profileButton = findViewById<Button>(R.id.btn_Profile)
        val feedbackButton = findViewById<Button>(R.id.btn_More)


        homeButton.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

//        notifyButton.setOnClickListener{
//            val intent = Intent(this, Notification::class.java)
//            startActivity(intent)
//        }

        jobsButton.setOnClickListener {
            val intent = Intent(this, Job_Poster_register::class.java)
            startActivity(intent)
        }

        profileButton.setOnClickListener {
            val intent = Intent(this, JobPoster_profile::class.java)
            startActivity(intent)
        }

        feedbackButton.setOnClickListener {
            val intent = Intent(this, Insert_Job::class.java)
            startActivity(intent)
        }
//        putNotificationButton.setOnClickListener{
//            val intent = Intent(this, NotificationAdd::class.java)
//            startActivity(intent)
//
//        }
    }
}