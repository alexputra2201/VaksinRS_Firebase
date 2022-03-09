package com.alex.firebaseauth

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase


class User_Rs_Adapter (val mCtx : Context, val layoutResId: Int, val rsList : List<RS>): ArrayAdapter<RS>(mCtx, layoutResId,
    rsList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)

        val view : View = layoutInflater.inflate(layoutResId, null)

        val tv_Nama : TextView = view.findViewById(R.id.tvNama)
        val tv_Alamat : TextView = view.findViewById(R.id.tvAlamat)
        val tv_Latitude : TextView = view.findViewById(R.id.tvLatitude)
        val tv_Longitude : TextView = view.findViewById(R.id.tvLongitude)

        val rs = rsList[position]

        tv_Nama.text = rs.nama
        tv_Alamat.text = rs.alamat
        tv_Latitude.text = rs.latitude
        tv_Longitude.text = rs.longitude

        return view
    }

    fun showUpdateDialog(rs: RS){
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Edit Data")
        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.update_dialog, null)

        val et_Nama = view.findViewById<EditText>(R.id.etNama)
        val et_Alamat = view.findViewById<EditText>(R.id.etAlamat)
        val et_Latitude = view.findViewById<EditText>(R.id.etLatitude)
        val et_Longitude = view.findViewById<EditText>(R.id.etLongitude)

        et_Nama.setText(rs.nama)
        et_Alamat.setText(rs.alamat)
        et_Latitude.setText(rs.latitude)
        et_Longitude.setText(rs.longitude)

        builder.setView(view)

        val alert = builder.create()
        alert.show()
    }
}