package com.example.jobposter.activities

import android.app.Activity
import android.app.Notification
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.jobposter.R
import com.example.jobposter.databinding.ActivityJobUserEditProfileBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage

class job_user_edit_profile : AppCompatActivity() {
    //viewbinding
    private lateinit var binding: ActivityJobUserEditProfileBinding

    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    //image URI
    private var imageUri: Uri?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobUserEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        loadUserInfo()

        //---adding image to the profile
        binding.myProfile.setOnClickListener{
            showImageAttachMenu()
        }

        //---update button implementation
        binding.updatebtn.setOnClickListener{
            validateData()
        }

      //-----------------------------------toolbar button-----------------------------------------

        val homeButton =  findViewById<Button>(R.id.btn_Home)
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
            val intent = Intent(this, job_user_profile::class.java)
            startActivity(intent)
        }

        feedbackButton.setOnClickListener{
            val intent = Intent(this, activity_job_user_applyjob::class.java)
            startActivity(intent)
        }
        //tool bar end
    }

    private var name=""
    private var phone=""
    private var email=""
    private var age =""
    private var location=""
    private fun validateData() {
        //get data
        name = binding.editTextTextPersonname.text.toString().trim()
        phone = binding.editTextTextPersonNnumber.text.toString().trim()
        email = binding.editTextTextPersonemail.text.toString().trim()
        age = binding.editTextTextPersonage.text.toString().trim()
        location=binding.editTextTextPersonlocation.text.toString().trim()

        //validate data
        if(name.isEmpty()){
            Toast.makeText(this,"Enter a name to process", Toast.LENGTH_SHORT).show()
        }
        else if (phone.isEmpty()){
            Toast.makeText(this, "Enter a phone number to proceed", Toast.LENGTH_SHORT).show()
        }
        else{
            //values are entered
            if(imageUri==null){
                //update without an image
                updateProfile("")
            }
            else{
                //with image
                uploadImage()
            }
        }
    }

    private fun uploadImage() {
        //image path and name , use uid to replace previous
        val filePathAndName = "Profile/"+firebaseAuth.uid

        //storage reference
        val reference = FirebaseStorage.getInstance().getReference(filePathAndName)
        reference.putFile(imageUri!!)
            .addOnSuccessListener {taskSnapshot->
                //get image url
                val uriTask: Task<Uri> = taskSnapshot.storage.downloadUrl
                while (!uriTask.isSuccessful);
                val uploadedImageUrl = "${uriTask.result}"
                updateProfile(uploadedImageUrl)
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Failed to upload image due to ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun updateProfile(uploadedImageUri: String) {
        //setup to enter data to db
        val hashMap:HashMap<String, Any> = HashMap()
        hashMap["name"]= "$name"
        hashMap["phone"]= "$phone"
        hashMap["email"]= "$email"
        hashMap["age"]= "$age"
        hashMap["location"]= "$location"
        if(imageUri != null){
            hashMap["profileImage"] = uploadedImageUri
        }

        //update to db
        val reference = FirebaseDatabase.getInstance("https://podiwadak-a4d20-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("jobseeker")
        reference.child(firebaseAuth.uid!!)
            .updateChildren(hashMap)
            .addOnSuccessListener {
                //profile update
                Toast.makeText(this, "User information updated...", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@job_user_edit_profile, job_user_profile::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Failed to update profile due to ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun loadUserInfo() {
        //db reference to get user info
        var ref = FirebaseDatabase.getInstance("https://podiwadak-a4d20-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("jobseeker")
        ref.child(firebaseAuth.uid!!)
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //getting user info
                    val name = "${snapshot.child("name").value}"
                    val profileImage = "${snapshot.child("profileImage").value}"
                    val email = "${snapshot.child("email").value}"
                    val phone = "${snapshot.child("phone").value}"
                    val location= "${snapshot.child("locatioon").value}"
                    val age = "${snapshot.child("age").value}"

                    //convert timestamp to proper date format

                    //set data to page
                    binding.editTextTextPersonNnumber.setText(phone)
                    binding.editTextTextPersonname.setText(name)
                    binding.editTextTextPersonemail.setText(email)
                    binding.editTextTextPersonname.setText(location)
                    binding.editTextTextPersonname.setText(age)


                    //set image
                    //glide library to get images from
                    // firebase
                    try {
                        Glide.with(this@job_user_edit_profile)
                            .load(profileImage)
                            .placeholder(R.drawable.ic_person)
                            .into(binding.myProfile)
                    }catch (e: Exception){

                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun showImageAttachMenu(){
        //show popup menu with Option to pick image from Gallery

        //setup popup menu
        val popupMenu = PopupMenu(this, binding.myProfile)
        popupMenu.menu.add(Menu.NONE, 0, 0, "Camera")
        popupMenu.menu.add(Menu.NONE, 1, 1, "Gallery")
        popupMenu.show()

        //handle popup menu item click
        popupMenu.setOnMenuItemClickListener {item->
            //get id of the item clicked
            val id = item.itemId
            if(id==0){
                //camera clicked
                pickImageCamera()
            }else if(id==1){
                //Gallery clicked
                pickImageGallery()
            }
            true
        }
    }

    private fun pickImageCamera() {
        //intent to pick image from camera
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Temp_Title")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Temp_Description")

        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        cameraActivityResultLauncher.launch(intent)
    }

    private val cameraActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback <ActivityResult>{ result->
            if(result.resultCode == Activity.RESULT_OK){
                val data = result.data
                imageUri = data!!.data
                //set to image view
                binding.myProfile.setImageURI(imageUri)
            }else{
                //cancelled
                Toast.makeText(this, "Cancelled...", Toast.LENGTH_SHORT).show()
            }
        }
    )

    private fun pickImageGallery(){
        //intent to pick image from the gallery
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryActivityResultLauncher.launch(intent)
    }

    private val galleryActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback <ActivityResult>{ result->
            if(result.resultCode == RESULT_OK){
                val data = result.data
                imageUri = data!!.data
                //set to image view
                binding.myProfile.setImageURI(imageUri)
            }else{
                //cancelled
                Toast.makeText(this, "Cancelled...", Toast.LENGTH_SHORT).show()
            }
        }
    )


}