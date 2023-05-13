package com.example.jobposter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class job_user_applyview : AppCompatActivity() {
    private lateinit var applyjobRecyclerView: RecyclerView
    private lateinit var jobLoadingData : TextView
    private lateinit var myjobList: ArrayList<applyjobDataClass>
    private lateinit var dbRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_job_apply_view)

        applyjobRecyclerView=findViewById(R.id.jobapplyview)
        applyjobRecyclerView.layoutManager = LinearLayoutManager(this)
        applyjobRecyclerView.setHasFixedSize(true)
        jobLoadingData = findViewById(R.id.jobLoadingData)

        myjobList = arrayListOf<applyjobDataClass>()
        getapplyjobData()

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

        notifyButton.setOnClickListener{
            val intent = Intent(this, job_user_applyview::class.java)
            startActivity(intent)
        }

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
//-----------------------------------end toolbar button-----------------------------------------
    }

    private fun getapplyjobData() {
        applyjobRecyclerView.visibility = View.GONE
        jobLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance("https://podiwadak-a4d20-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("applyemployee")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                myjobList.clear()
                if (snapshot.exists()){
                    for (notifySnap in snapshot.children){
                        val notifyData = notifySnap.getValue(applyjobDataClass::class.java)
                        myjobList.add(notifyData!!)
                    }
                    val myAdapter = myjobAdapter(myjobList)
                    applyjobRecyclerView.adapter = myAdapter

                    applyjobRecyclerView.visibility= View.VISIBLE
                    jobLoadingData.visibility= View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}