package com.alex.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import androidx.appcompat.widget.Toolbar
import com.alex.firebaseauth.databinding.ActivityVaksinRsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class activity_vaksin_rs : AppCompatActivity(), View.OnClickListener {

    companion object{
        const val EXTRA_NAMA = "extra_nama"
        const val EXTRA_ID = "extra_id"
    }


    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityVaksinRsBinding
    private lateinit var listVaksin : ListView
    private lateinit var ref: DatabaseReference
    private lateinit var vaksinList: MutableList<Vaksin>

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityVaksinRsBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val toolbar: Toolbar = binding.toolbar
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)


            auth = FirebaseAuth.getInstance()
            val id = intent.getStringExtra(admin_activityvaksin.EXTRA_ID)
            ref = FirebaseDatabase.getInstance().getReference("Vaksin").child(id.toString())


            listVaksin = findViewById(R.id.lv_vaksin_rs)

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
                        val adapter = User_vaksin_rs_adapter(this@activity_vaksin_rs, R.layout.item_view_vaksin, vaksinList)
                        listVaksin.adapter = adapter
                    }
                }
            })

            listVaksin.setOnItemClickListener{ parent, view, position, id ->

                val Vaksin = vaksinList.get(position)

                val intent = Intent(this@activity_vaksin_rs, view_booking::class.java)
                intent.putExtra(admin_activityvaksin.EXTRA_ID, Vaksin.id_v)
                intent.putExtra(admin_activityvaksin.EXTRA_NAMA, Vaksin.nama)
                intent.putExtra("nama_vaksin", Vaksin.nama)
                startActivity(intent)
            }
        }

        override fun onClick(v: View?) {

        }


        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.option_menu_user, menu)
            return true
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when(item.itemId){
                R.id.logout -> {
                    auth.signOut()
                    Intent(this@activity_vaksin_rs, LoginActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
                    return true
                }
                R.id.RS -> {
                    Intent(this@activity_vaksin_rs, user_rs::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
                    return true
                }
                else -> return true
            }
        }
    }