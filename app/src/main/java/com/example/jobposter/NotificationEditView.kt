package com.example.jobposter

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase


class NotificationEditView : AppCompatActivity (){

    private lateinit var tvName: TextView
    private lateinit var tvJobTitel: TextView
    private lateinit var tvPhone: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvNotification: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_edit_view)

            initView()
            setValuesToViews()
        //Update Button call------------------------------
        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("id").toString(),
            )
        } //end update button ------------------------------


        //Delete button call-------------------------------
        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("id").toString()
            )
        }//-----------------------------------------------


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
        }
//-------------------------------end toolbar---------------------------------------------

    // View Edit data---------------------------------------
    private fun initView(){

        tvName = findViewById(R.id.tvName)
        tvJobTitel = findViewById(R.id.tvJobTitel)
        tvPhone = findViewById(R.id.tvPhone)
        tvEmail = findViewById(R.id.tvEmail)
        tvNotification = findViewById(R.id.tvNotification)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews(){
        tvName.text = intent.getStringExtra("name")
        tvJobTitel.text = intent.getStringExtra("jobTitel")
        tvPhone.text = intent.getStringExtra("phoneNumber")
        tvEmail.text = intent.getStringExtra("email")
        tvNotification.text = intent.getStringExtra("notification")
    }
    // End view data --------------------------------------


    // Start Updata function-------------------------------------
    private fun openUpdateDialog(
        id: String,
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_notification_update, null)

        mDialog.setView(mDialogView)

        val upName = mDialogView.findViewById<EditText>(R.id.upName)
        val upEmail = mDialogView.findViewById<EditText>(R.id.upEmail)
        val upPhone = mDialogView.findViewById<EditText>(R.id.upPhone)
        val upJobTitel = mDialogView.findViewById<EditText>(R.id.upJobTitel)
        val upNotification = mDialogView.findViewById<EditText>(R.id.upNotification)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        upName.setText(intent.getStringExtra("name").toString())
        upEmail.setText(intent.getStringExtra("email").toString())
        upPhone.setText(intent.getStringExtra("phoneNumber").toString())
        upJobTitel.setText(intent.getStringExtra("jobTitel").toString())
        upNotification.setText(intent.getStringExtra("notification").toString())


        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener{
            updateNotifyData(
                id,
                upName.text.toString(),
                upEmail.text.toString(),
                upPhone.text.toString(),
                upJobTitel.text.toString(),
                upNotification.text.toString(),
            )
            Toast.makeText(applicationContext,"Notification Updated",Toast.LENGTH_LONG).show()

            //setting updated data to textview
            tvName.text = upName.text.toString()
            tvJobTitel.text = upJobTitel.text.toString()
            tvPhone.text = upPhone.text.toString()
            tvEmail.text = upEmail.text.toString()
            tvNotification.text = upNotification.text.toString()

            alertDialog.dismiss()
        }
    }
    private fun updateNotifyData(
        id: String,
        name: String,
        email : String,
        phoneNumber : String,
        jobTitel : String,
        notification : String
    ){
        val dbRef = FirebaseDatabase.getInstance("https://podiwadak-a4d20-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Notifications").child(id)
        val notifyInfo = NotificationDataClass(id, name, email, phoneNumber, jobTitel, notification)
        dbRef.setValue(notifyInfo)
    }
    //end Updata -------------------------------------------


    // Delete Function-------------------------------------
    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance("https://podiwadak-a4d20-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Notifications").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Notification data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, NotificationAdd::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }
    // End Delete function----------------------------------

}