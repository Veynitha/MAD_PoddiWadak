package com.example.jobposter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobposter.R
import com.example.jobposter.models.JobModel

class JobAdapter (private val joblist: ArrayList<JobModel>) : RecyclerView.Adapter<JobAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.job_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: JobAdapter.ViewHolder, position: Int) {
        val currentJob = joblist[position]
        holder.title.text = currentJob.jobTitle
        holder.location.text = currentJob.location
        holder.payment.text = currentJob.payment
    }

    override fun getItemCount(): Int {
        return joblist.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val title : TextView = itemView.findViewById(R.id.tv_JobTitle)
        val payment : TextView = itemView.findViewById(R.id.tv_Payment)
        val location : TextView = itemView.findViewById(R.id.tv_JobLocation)
    }

}
