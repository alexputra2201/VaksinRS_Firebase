package com.alex.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.alex.firebaseauth.databinding.ActivityViewBookingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class view_booking : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var ref: DatabaseReference
    private lateinit var binding: ActivityViewBookingBinding
    private lateinit var btnSave: Button
    companion object{
        const val EXTRA_NAMA = "extra_nama"
        const val EXTRA_ID = "extra_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewBookingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        btnSave = findViewById(R.id.button_vaksin)
        val id = intent.getStringExtra(activity_vaksin_rs.EXTRA_ID)
        ref = FirebaseDatabase.getInstance().getReference("Booking").child(id.toString())

        if(intent.hasExtra("nama_vaksin")){
            val nama_vaksin:String = this.intent.getStringExtra("nama_vaksin").toString()
            setDetil(nama_vaksin)
        }
        btnSave.setOnClickListener{
            saveData()
            val intent = Intent(this@view_booking, ActivityHistory::class.java)
            startActivity(intent)
        }


    }
    fun setDetil(nama_vaksin:String ) {
        binding.etNamaVaksin.text=nama_vaksin
    }
    private fun saveData() {
        val waktu = binding.etWaktu.text.toString().trim()
        val nama = binding.etNama.text.toString().trim()
        if (waktu.isEmpty()) {
            binding.etWaktu.error = "Harap Isi Waktu!"
            return
        }
        if (waktu.isEmpty()) {
            binding.etNama.error = "Harap Isi Email!"
            return
        }

        val bookingId = ref.push().key
        val booking = Booking(bookingId,waktu,nama)
        if (bookingId != null) {
            ref.child(bookingId).setValue(booking).addOnCompleteListener {
                Toast.makeText(applicationContext, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu_user, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout -> {
                auth.signOut()
                Intent(this@view_booking, LoginActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
                return true
            }
            R.id.RS -> {
                Intent(this@view_booking, user_rs::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
                return true
            }
            R.id.History -> {
                Intent(this@view_booking, ActivityHistory::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
                return true
            }
            else -> return true
        }
    }
}