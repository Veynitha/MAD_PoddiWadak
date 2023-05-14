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

        var perhour = calcHourlyIncome()
        binding.tvPay.text = perhour.toString()
        setView()
    }


    private fun calcHourlyIncome(): Int {
        var totpay = intent.getStringExtra("jobPayment")
        var hours = intent.getStringExtra("hours")
        var hourlyincome = (totpay!!.toInt()/hours!!.toInt())
        return hourlyincome
    }

    private fun setView() {
        binding.tvJobTitle.text = intent.getStringExtra("jobTitle")
        binding.tvHours.text = intent.getStringExtra("hours")
        binding.tvJobPayment.text = intent.getStringExtra("jobPayment")
        binding.tvJobLocation.text = intent.getStringExtra("jobLocation")
        binding.tvContactNumber.text = intent.getStringExtra("contactNo")
        binding.tvJobDescription.text = intent.getStringExtra("jobDescription")

    }
}