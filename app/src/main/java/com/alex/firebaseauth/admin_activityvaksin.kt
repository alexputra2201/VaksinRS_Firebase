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
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.alex.firebaseauth.databinding.ActivityActivityvaksinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class admin_activityvaksin : AppCompatActivity(), View.OnClickListener  {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityActivityvaksinBinding
    private lateinit var et_Nama: EditText
    private lateinit var btnSave: Button
    private lateinit var listVaksin: ListView


    companion object{
        const val EXTRA_NAMA = "extra_nama"
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var ref: DatabaseReference
    private lateinit var vaksinList: MutableList<Vaksin>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActivityvaksinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        auth = FirebaseAuth.getInstance()

        val navController =  findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.nav_home, R.id.nav_profile
        ).build()

        setupActionBarWithNavController(navController, appBarConfiguration)
        val id = intent.getStringExtra(EXTRA_ID)

        ref = FirebaseDatabase.getInstance().getReference("Vaksin").child(id.toString())

        et_Nama = findViewById(R.id.etNama)
        btnSave = findViewById(R.id.btn_Save)
        listVaksin = findViewById(R.id.lv_vaksin)
        btnSave.setOnClickListener(this)

        vaksinList = mutableListOf()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    vaksinList.clear()
                    for (h in p0.children) {
                        val vaksin = h.getValue(Vaksin::class.java)
                        if (vaksin != null) {
                            vaksinList.add(vaksin)
                        }
                    }
                    val adapter = VaksinAdapter(this@admin_activityvaksin, R.layout.item_vaksin, vaksinList)
                    listVaksin.adapter = adapter
                }
            }
        })
    }

    override fun onClick(v: View?) {
        saveData()
    }

    private fun saveData() {
        val nama = et_Nama.text.toString().trim()

        if (nama.isEmpty()) {
            et_Nama.error = "Harap Isi Nama!"
            return
        }

        val vaksinId = ref.push().key

        val vaksin = Vaksin(vaksinId, nama)
        if (vaksinId != null) {
            ref.child(vaksinId).setValue(vaksin).addOnCompleteListener {
                Toast.makeText(applicationContext, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout -> {
                auth.signOut()
                Intent(this@admin_activityvaksin, LoginActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
                return true
            }
            R.id.RS -> {
                Intent(this@admin_activityvaksin, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
                return true
            }
            else -> return true
        }
    }

}