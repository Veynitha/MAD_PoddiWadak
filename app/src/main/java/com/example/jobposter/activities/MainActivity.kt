package com.example.jobposter.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.jobposter.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeButton = findViewById<Button>(R.id.btn_Home)
        val notifyButton = findViewById<Button>(R.id.btn_Notification)
        val jobsButton = findViewById<Button>(R.id.btn_Jobs)
        val profileButton = findViewById<Button>(R.id.btn_Profile)
        val feedbackButton = findViewById<Button>(R.id.btn_More)

        homeButton.setOnClickListener{
            val intent = Intent(this@MainActivity, job_user_register::class.java)
            startActivity(intent)
        }
        notifyButton.setOnClickListener{
            val intent = Intent(this@MainActivity, Job_Poster_register::class.java)
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


    }
}