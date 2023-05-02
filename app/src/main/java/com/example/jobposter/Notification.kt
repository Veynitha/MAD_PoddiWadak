package com.example.jobposter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class Notification: AppCompatActivity() {

    private lateinit var notifyRecyclerView: RecyclerView
    private lateinit var tvLoadingData : TextView
    private lateinit var notifyList: ArrayList<NotificationDataClass>
    private lateinit var dbRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        notifyRecyclerView=findViewById(R.id.notifyView)
        notifyRecyclerView.layoutManager = LinearLayoutManager(this)
        notifyRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        notifyList = arrayListOf<NotificationDataClass>()
        getNotificationData()

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
            val intent = Intent(this, Notification::class.java)
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

    private fun getNotificationData() {
        notifyRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Notifications")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                notifyList.clear()
                if (snapshot.exists()){
                    for (notifySnap in snapshot.children){
                        val notifyData = notifySnap.getValue(NotificationDataClass::class.java)
                        notifyList.add(notifyData!!)
                    }
                    val mAdapter = NotifictionAdapter(notifyList)
                    notifyRecyclerView.adapter = mAdapter

                    notifyRecyclerView.visibility= View.VISIBLE
                    tvLoadingData.visibility= View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}