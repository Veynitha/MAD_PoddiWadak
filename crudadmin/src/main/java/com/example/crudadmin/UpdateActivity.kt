package com.example.crudadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.crudadmin.databinding.ActivityUpdateBinding
import com.example.crudadmin.databinding.ActivityUploadBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.updateName.setOnClickListener {
            val referencePhone = binding.referencePhone.text.toString()
            val updateName = binding.updateName.text.toString()
            val updateLocation = binding.updateFeedback.text.toString()
            val updateEmail = binding.updateFeedback.text.toString()

            updateData(referencePhone,updateName, updateLocation,updateEmail )

        }
    }

    private fun updateData(phone: String, name: String, email: String, feedback: String){
        databaseReference = FirebaseDatabase.getInstance().getReference("Phone Directory")
        val user = mapOf<String, String>("name" to name, "email" to email, "phone" to phone, "feedback" to feedback)
        databaseReference.child(phone).updateChildren(user).addOnSuccessListener {
            binding.referencePhone.text.clear()
            binding.UpdateEmail.text.clear()
            binding.updateFeedback.text.clear()
            binding.updateName.text.clear()
            Toast.makeText(this,"Updated",Toast.LENGTH_SHORT).show()

        }.addOnFailureListener{
            Toast.makeText(this,"Unable to Update",Toast.LENGTH_SHORT).show()
        }
    }
}