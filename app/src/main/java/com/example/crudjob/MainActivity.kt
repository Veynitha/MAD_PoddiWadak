package com.example.crudjob

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.widget.Toast
import com.example.crudjob.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchButton.setOnClickListener{
            val searchPhone: String = binding.searchPhone.text.toString()
            if(searchPhone.isNotEmpty()){
                readData(searchPhone)
            }else{
                Toast.makeText(this,"Please Enter the Phone number",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun readData(phone: String){
        databaseReference = FirebaseDatabase.getInstance().getReference("Phone Directory")
        databaseReference.child(phone).get().addOnSuccessListener {
            if (it.exists()){
                val name = it.child("name").value
                val email = it.child("email").value
                val feedback = it.child("feedback").value
                Toast.makeText(this,"Results Found",Toast.LENGTH_SHORT).show()
                binding.searchPhone.text.clear()
                binding.readName.text = name.toString()
                binding.readEmail.text = email.toString()
                binding.readfeedback.text = feedback.toString()
            }else
                Toast.makeText(this,"Phone number does not exist",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this,"Something Wrong",Toast.LENGTH_SHORT).show()
        }
    }
}