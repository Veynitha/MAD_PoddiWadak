package com.example.jobposter

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class First_page: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page)

        val firstButton = findViewById<Button>(R.id.but_first)


        firstButton.setOnClickListener{
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

    }
}