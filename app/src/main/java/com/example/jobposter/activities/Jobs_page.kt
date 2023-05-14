package com.example.jobposter.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobposter.adapters.JobAdapter
import com.example.jobposter.databinding.ActivityJobsPageBinding
import com.example.jobposter.models.JobModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.EventListener

class Jobs_page : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityJobsPageBinding

    //job model initialized as a arraylsit
    private lateinit var joblist: ArrayList<JobModel>

    //db reference
    private lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobsPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvJobs.layoutManager = LinearLayoutManager(this)
        binding.rvJobs.setHasFixedSize(true)

        joblist = arrayListOf<JobModel>()

        //retrieving job data
        getJobData()


        //-----------nav bar implementation-------------------//
        binding.btnHome.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.btnNotification.setOnClickListener{

        }
        binding.btnJobs.setOnClickListener{
            val intent = Intent(this, Jobs_page::class.java)
            startActivity(intent)
        }
        binding.btnProfile.setOnClickListener{
            val intent = Intent(this, JobPoster_login::class.java)
            startActivity(intent)
        }
        binding.btnMore.setOnClickListener{
            val intent = Intent(this, Insert_Job::class.java)
            startActivity(intent)
        }
    }

    private fun getJobData() {
        //setting visibility of recycler view and placeholder tv
        binding.rvJobs.visibility = View.GONE
        binding.tvLoadingData.visibility = View.VISIBLE

        ref = FirebaseDatabase.getInstance("https://podiwadak-a4d20-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Jobs")

        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                joblist.clear()
                if(snapshot.exists()){
                    for(jobSnap in snapshot.children){
                        //getting values from db
                        val jobData = jobSnap.getValue(JobModel::class.java)
                        joblist.add(jobData!!)
                    }
                    val mAdapter = JobAdapter(joblist)
                    binding.rvJobs.adapter = mAdapter

                    mAdapter.setonItemClickListener(object: JobAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            //navigating to the job details page
                            val intent = Intent(this@Jobs_page, JobDetails::class.java)

                            //sending data abt the job selected to the details page
                            intent.putExtra("jobId", joblist[position].jobId)
                            intent.putExtra("jobTitle", joblist[position].jobTitle)
                            intent.putExtra("hours", joblist[position].hours)
                            intent.putExtra("jobPayment", joblist[position].payment)
                            intent.putExtra("jobLocation", joblist[position].location)
                            intent.putExtra("jobDescription", joblist[position].description)
                            intent.putExtra("contactNo", joblist[position].contactNo)
                            startActivity(intent)
                        }

                    })

                    //changing visibility of recycler view and placeholder tv
                    binding.rvJobs.visibility = View.VISIBLE
                    binding.tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}