package com.alex.firebaseauth

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.alex.firebaseauth.databinding.ViewRsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class view_rs: AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ViewRsBinding
    private lateinit var listRS: ListView
    private lateinit var ref: DatabaseReference
    private lateinit var rsList: MutableList<RS>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ViewRsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        auth = FirebaseAuth.getInstance()




        ref = FirebaseDatabase.getInstance().getReference("RS")


        listRS = findViewById(R.id.lv_rs)

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
                    val adapter = RSAdapter(this@view_rs, R.layout.item_rs, rsList)
                    listRS.adapter = adapter
                }
            }
        })

        listRS.setOnItemClickListener{ parent, view, position, id ->
            val RS = rsList.get(position)


            val intent = Intent(this@view_rs, view_booking::class.java)
            startActivity(intent)
        }
    }

    override fun onClick(v: View?) {

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout -> {
                auth.signOut()
                Intent(this@view_rs, LoginActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
                return true
            }
            R.id.RS -> {
                Intent(this@view_rs, view_rs::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
                return true
            }
            else -> return true
        }
    }
}