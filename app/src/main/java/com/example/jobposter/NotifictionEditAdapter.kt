package com.example.jobposter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotifictionEditAdapter(private val notifyList: ArrayList<NotificationDataClass>) :
    RecyclerView.Adapter<NotifictionEditAdapter.ViewHolder>() {

    //---------------editview---------------
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }
    //---------------------------------------------

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_notification_list_itme,parent,false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val currentNotify = notifyList[position]

        holder.tvName.text = currentNotify.name
        holder.tvJobTitel.text = currentNotify.jobTitel
        holder.tvNotification.text =currentNotify.notification
        holder.tvPhone.text = currentNotify.phoneNumber
        holder.tvEmail.text= currentNotify.email
    }

    override fun getItemCount(): Int {
        return notifyList.size
    }

    class ViewHolder(itemView: View,clickListener: onItemClickListener): RecyclerView.ViewHolder(itemView) {

        val tvName : TextView = itemView.findViewById(R.id.tvName)
        val tvJobTitel : TextView = itemView.findViewById(R.id.tvJobTitel)
        val tvNotification: TextView = itemView.findViewById(R.id.tvNotification)
        val tvPhone : TextView = itemView.findViewById(R.id.tvPhone)
        val tvEmail : TextView = itemView.findViewById(R.id.tvEmail)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }

    }

}