package com.example.jobposter

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.jobposter.databinding.ActivityJobPosterEditProfileBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.lang.Exception

class Job_Poster_editProfile : AppCompatActivity() {

    //viewbinding
    private lateinit var binding: ActivityJobPosterEditProfileBinding

    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    //image URI
    private var imageUri:Uri?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobPosterEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        loadUserInfo()

        //---adding image to the profile
        binding.ivProfile.setOnClickListener{
            showImageAttachMenu()
        }

        //---update button implementation
        binding.btnEditProfile.setOnClickListener{
            validateData()
        }

        //---back button implementation
        binding.btnBack.setOnClickListener{
            startActivity(Intent(this@Job_Poster_editProfile, JobPoster_profile::class.java))
        }
    }

    private var name=""
    private var phone=""

    private fun validateData() {
        //get data
        name = binding.etEditFullName.text.toString().trim()
        phone = binding.etEditPhone.text.toString().trim()

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
        if(imageUri != null){
            hashMap["profileImage"] = uploadedImageUri
        }

        //update to db
        val reference = FirebaseDatabase.getInstance("https://podiwadak-a4d20-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
        reference.child(firebaseAuth.uid!!)
            .updateChildren(hashMap)
            .addOnSuccessListener {
                //profile update
                Toast.makeText(this, "User information updated...", Toast.LENGTH_SHORT).show()
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
        var ref = FirebaseDatabase.getInstance("https://podiwadak-a4d20-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
        ref.child(firebaseAuth.uid!!)
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //getting user info
                    var name = "${snapshot.child("name").value}"
                    val profileImage = "${snapshot.child("profileImage").value}"
                    val phone = "${snapshot.child("phone").value}"

                    //convert timestamp to proper date format

                    //set data to page
                   binding.etEditPhone.setText(phone)
                   binding.etEditFullName.setText(name)

                    //set image
                    //glide library to get images from firebase
                    try {
                        Glide.with(this@Job_Poster_editProfile)
                            .load(profileImage)
                            .placeholder(R.drawable.ic_person)
                            .into(binding.ivProfile)
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
        val popupMenu = PopupMenu(this, binding.ivProfile)
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
                binding.ivProfile.setImageURI(imageUri)
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
            if(result.resultCode == Activity.RESULT_OK){
                val data = result.data
                imageUri = data!!.data
                //set to image view
                binding.ivProfile.setImageURI(imageUri)
            }else{
                //cancelled
                Toast.makeText(this, "Cancelled...", Toast.LENGTH_SHORT).show()
            }
        }
    )


}