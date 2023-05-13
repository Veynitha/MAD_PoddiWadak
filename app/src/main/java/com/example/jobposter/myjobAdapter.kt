package com.example.jobposter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class myjobAdapter (private val  myjobList: ArrayList<applyjobDataClass>) :
    RecyclerView.Adapter<myjobAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_job_user_applyview,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val currentapplyjob = myjobList[position]

        holder.tvjobName.text = currentapplyjob.job
        holder.tvusername.text = currentapplyjob.name
        holder.tvdescription.text =currentapplyjob.note
        holder.tvage.text = currentapplyjob.age
        holder.tvPhone.text= currentapplyjob.contactnum
    }

    override fun getItemCount(): Int {
        return myjobList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val tvjobName : TextView = itemView.findViewById(R.id.tvjobName)
        val tvusername : TextView = itemView.findViewById(R.id.tvusername)
        val tvdescription: TextView = itemView.findViewById(R.id.tvdescription)
        val tvage : TextView = itemView.findViewById(R.id.tvPhone)
        val tvPhone : TextView = itemView.findViewById(R.id.tvage)


    }


}