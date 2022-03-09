package com.alex.firebaseauth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.*
import com.alex.firebaseauth.databinding.ActivityTambahRsBinding
import android.content.Intent




class activity_tambah_rs : AppCompatActivity(), View.OnClickListener {
    private lateinit var et_Nama: EditText
    private lateinit var et_Alamat: EditText
    private lateinit var et_Latitude: EditText
    private lateinit var et_Longitude: EditText
    private lateinit var btnSave: Button
    private lateinit var listRS: ListView

    private lateinit var binding: ActivityTambahRsBinding

    private lateinit var ref: DatabaseReference
    private lateinit var rsList: MutableList<RS>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahRsBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_tambah_rs)

        ref = FirebaseDatabase.getInstance().getReference("RS")

        et_Nama = findViewById(R.id.etNama)
        et_Alamat = findViewById(R.id.etAlamat)
        et_Latitude = findViewById(R.id.etLatitude)
        et_Longitude = findViewById(R.id.etLongitude)
        btnSave = findViewById(R.id.btn_Save)
        listRS = findViewById(R.id.lv_rs)
        btnSave.setOnClickListener(this)

        rsList = mutableListOf()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    rsList.clear()
                    for (h in p0.children) {
                        val rs = h.getValue(RS::class.java)
                        if (rs != null) {
                            rsList.add(rs)
                        }
                    }
                    val adapter = RSAdapter(this@activity_tambah_rs, R.layout.item_rs, rsList)
                    listRS.adapter = adapter
                }
            }
        })
    }

    override fun onClick(v: View?) {
        saveData()
    }

    private fun saveData() {
        val nama = et_Nama.text.toString().trim()
        val alamat = et_Alamat.text.toString().trim()
        val latitude = et_Latitude.text.toString().trim()
        val longitude = et_Longitude.text.toString().trim()

        if (nama.isEmpty()) {
            et_Nama.error = "Harap Isi Nama!"
            return
        }

        if (alamat.isEmpty()) {
            et_Alamat.error = "Harap Isi Alamat!"
        }

        if (latitude.isEmpty()) {
            et_Latitude.error = "Harap Isi Latitude!"
        }

        if (longitude.isEmpty()) {
            et_Longitude.error = "Harap Isi Longitude!"
        }


        val rsId = ref.push().key

        val rs = RS(rsId, nama, alamat, latitude, longitude)
        if (rsId != null) {
            ref.child(rsId).setValue(rs).addOnCompleteListener {
                Toast.makeText(applicationContext, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }
}