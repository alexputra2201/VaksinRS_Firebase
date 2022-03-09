package com.alex.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.alex.firebaseauth.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HomeActivity : AppCompatActivity(), View.OnClickListener  {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityHomeBinding
    private lateinit var et_Nama: EditText
    private lateinit var et_Alamat: EditText
    private lateinit var et_Latitude: EditText
    private lateinit var et_Longitude: EditText
    private lateinit var btnSave: Button
    private lateinit var listRS: ListView


    private lateinit var ref: DatabaseReference
    private lateinit var rsList: MutableList<RS>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        auth = FirebaseAuth.getInstance()




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
                    val adapter = RSAdapter(this@HomeActivity, R.layout.item_rs, rsList)
                    listRS.adapter = adapter
                }
            }
        })

        listRS.setOnItemClickListener{ parent, view, position, id ->
            val RS = rsList.get(position)


            val intent = Intent(this@HomeActivity, admin_activityvaksin::class.java)
            intent.putExtra(admin_activityvaksin.EXTRA_ID, RS.id)
            intent.putExtra(admin_activityvaksin.EXTRA_NAMA, RS.nama)
            startActivity(intent)
        }
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

        et_Nama.setText("");
        et_Alamat.setText("");
        et_Latitude.setText("");
        et_Longitude.setText("");

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout -> {
                auth.signOut()
                Intent(this@HomeActivity, LoginActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
                return true
            }
            R.id.RS -> {
                Intent(this@HomeActivity, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
                return true
            }
            else -> return true
        }
    }
}