package com.alex.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import androidx.appcompat.widget.Toolbar
import com.alex.firebaseauth.databinding.ActivityUserRsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class user_rs : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityUserRsBinding
    private lateinit var listRS: ListView
    private lateinit var ref: DatabaseReference
    private lateinit var rsList: MutableList<RS>
    private lateinit var vaksinList: MutableList<Vaksin>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserRsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        auth = FirebaseAuth.getInstance()

        ref = FirebaseDatabase.getInstance().getReference("RS")


        listRS = findViewById(R.id.lv_user_rs)

        rsList = mutableListOf()
        vaksinList = mutableListOf()
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
                    val adapter = User_Rs_Adapter(this@user_rs, R.layout.item_view_rs, rsList)
                    listRS.adapter = adapter
                }
            }
        })

//        listRS.setOnItemClickListener{ parent, view, position, id ->
//            val RS = rsList.get(position)
//            //val Vaksin = vaksinList.get(position)
//            val intent = Intent(this@user_rs, activity_vaksin_rs::class.java)
//            intent.putExtra("nama",RS.nama)
//            intent.putExtra("alamat", RS.alamat)
//            intent.putExtra("latitude",RS.latitude)
//            intent.putExtra("longitude",RS.longitude)
//            //intent.putExtra("nama_vaksin", Vaksin.nama)
//            startActivity(intent)
//        }
        listRS.setOnItemClickListener{ parent, view, position, id ->
            val RS = rsList.get(position)


            val intent = Intent(this@user_rs, activity_vaksin_rs::class.java)
            intent.putExtra(admin_activityvaksin.EXTRA_ID, RS.id)
            intent.putExtra(admin_activityvaksin.EXTRA_NAMA, RS.nama)
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
                Intent(this@user_rs, LoginActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
                return true
            }
            R.id.RS -> {
                Intent(this@user_rs, user_rs::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
                return true
            }
            R.id.History -> {
                Intent(this@user_rs, ActivityHistory::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
                return true
            }
            else -> return true
        }
    }
}