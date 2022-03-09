package com.alex.firebaseauth

import android.app.Dialog
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.alex.firebaseauth.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class Profile : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var  databaseReference : DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri: Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        binding.saveBtn.setOnClickListener{


            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val bio = binding.etBio.text.toString()

            val user = User(firstName,lastName,bio)
            if(uid != null){
                databaseReference.child(uid).setValue(user).addOnCompleteListener{

                    if(it.isSuccessful){

                        uploadProfilePic()

                    }else{
                        Toast.makeText(this@Profile,"Failed to update profile", Toast.LENGTH_SHORT).show()

                    }
                }
            }

        }

    }


    private fun uploadProfilePic() {

        imageUri = Uri.parse("android.resource://$packageName./$(R.drawable.profile)")
        storageReference = FirebaseStorage.getInstance().getReference("Users/"+auth.currentUser?.uid)
        storageReference.putFile(imageUri).addOnSuccessListener {

            Toast.makeText(this@Profile, "Profile successfuly updated", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener{


            Toast.makeText(this@Profile, "Failed to update profile", Toast.LENGTH_SHORT).show()
        }

    }



}