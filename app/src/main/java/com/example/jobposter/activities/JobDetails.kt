package com.example.jobposter.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jobposter.R
import com.example.jobposter.databinding.ActivityJobDetailsBinding


class JobDetails : AppCompatActivity() {

    private lateinit var binding: ActivityJobDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
    }

    private fun setView() {
        binding.tvJobTitle.text = intent.getStringExtra("jobTitle")
        binding.tvJobType.text = intent.getStringExtra("jobType")
        binding.tvJobPayment.text = intent.getStringExtra("jobPayment")
        binding.tvJobLocation.text = intent.getStringExtra("jobLocation")
        binding.tvContactNumber.text = intent.getStringExtra("contactNo")
        binding.tvJobDescription.text = intent.getStringExtra("jobDescription")

    }
}