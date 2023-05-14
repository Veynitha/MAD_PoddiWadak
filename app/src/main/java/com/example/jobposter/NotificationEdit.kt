package com.example.jobposter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobposter.activities.Insert_Job
import com.example.jobposter.activities.JobPoster_login
import com.example.jobposter.activities.Jobs_page
import com.google.firebase.database.*

class NotificationEdit: AppCompatActivity() {

    //Admin data edit Notification insert part
    private lateinit var notifyRecyclerView: RecyclerView
    private lateinit var tvLoadingData : TextView
    private lateinit var notifyList: ArrayList<NotificationDataClass>
    private lateinit var dbRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_edit)

        notifyRecyclerView=findViewById(R.id.notifyEditView)
        notifyRecyclerView.layoutManager = LinearLayoutManager(this)
        notifyRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        notifyList = arrayListOf <NotificationDataClass>()
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
    //-----------------------------------toolbar button-----------------------------------------
    }
    //Admin data edit Notification insert part
    private fun getNotificationData() {
        notifyRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance("https://podiwadak-a4d20-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Notifications")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                notifyList.clear()
                if (snapshot.exists()){
                    for (notifySnap in snapshot.children){
                        val notifyData = notifySnap.getValue(NotificationDataClass::class.java)
                        notifyList.add(notifyData!!)
                    }
                    val mAdapter = NotifictionEditAdapter(notifyList)
                    notifyRecyclerView.adapter = mAdapter

                    //-----------editview--------------
                    mAdapter.setOnItemClickListener(object : NotifictionEditAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent (this@NotificationEdit, NotificationEditView::class.java)

                            //put extras
                            intent.putExtra("id", notifyList[position].id)
                            intent.putExtra("name", notifyList[position].name)
                            intent.putExtra("jobTitel", notifyList[position].jobTitel)
                            intent.putExtra("phoneNumber", notifyList[position].phoneNumber)
                            intent.putExtra("email", notifyList[position].email)
                            intent.putExtra("notification", notifyList[position].notification)
                            startActivity(intent)
                        }

                    })

                    //-------------------------pa

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