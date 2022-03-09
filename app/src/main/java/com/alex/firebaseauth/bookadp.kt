package com.alex.firebaseauth

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class bookadp (val mCtx : Context, val layoutResId: Int, val rsList : List<RS>, val vaksinList : List<Vaksin>): ArrayAdapter<RS>(mCtx, layoutResId,
    rsList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)

        val view: View = layoutInflater.inflate(layoutResId, null)

        val tv_Nama: TextView = view.findViewById(R.id.tvNama)
        val tv_Alamat: TextView = view.findViewById(R.id.tvAlamat)
        val tv_Latitude: TextView = view.findViewById(R.id.tvLatitude)
        val tv_Longitude: TextView = view.findViewById(R.id.tvLongitude)
        val tv_Edit: TextView = view.findViewById(R.id.tvEdit)
        val tv_nama_vaksin: TextView = view.findViewById(R.id.etNamaVaksin)


        val rs = rsList[position]
        val vaksin = vaksinList[position]

        tv_Edit.setOnClickListener {
            showUpdateDialog(rs, vaksin)
        }

        tv_Nama.text = rs.nama
        tv_Alamat.text = rs.alamat
        tv_Latitude.text = rs.latitude
        tv_Longitude.text = rs.longitude
        tv_nama_vaksin.text = vaksin.nama

        return view
    }

    fun showUpdateDialog(rs: RS, vaksin: Vaksin) {
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Edit Data")
        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.update_dialog_booking, null)

        val et_Nama = view.findViewById<TextView>(R.id.etNama)
        val et_Alamat = view.findViewById<TextView>(R.id.etAlamat)
        val et_Latitude = view.findViewById<TextView>(R.id.etLatitude)
        val et_Longitude = view.findViewById<TextView>(R.id.etLongitude)
        val et_nama_vaksin = view.findViewById<TextView>(R.id.etNamaVaksin)

        et_Nama.setText(rs.nama)
        et_Alamat.setText(rs.alamat)
        et_Latitude.setText(rs.latitude)
        et_Longitude.setText(rs.longitude)
        et_nama_vaksin.setText(vaksin.nama)


        builder.setView(view)
    }
}