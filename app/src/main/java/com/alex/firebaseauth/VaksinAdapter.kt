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

class VaksinAdapter (val mCtx : Context, val layoutResId: Int, val vaksinList: List<Vaksin>): ArrayAdapter<Vaksin>(mCtx, layoutResId,
    vaksinList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)

        val view : View = layoutInflater.inflate(layoutResId, null)

        val tv_Nama : TextView = view.findViewById(R.id.tvNama)
        val tv_Edit : TextView = view.findViewById(R.id.tvEdit)


        val vaksin = vaksinList[position]

        tv_Edit.setOnClickListener{
            showUpdateDialog(vaksin)
        }

        tv_Nama.text = vaksin.nama

        return view
    }
    fun showUpdateDialog(vaksin: Vaksin){
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Edit Data")
        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.update_dialog_vaksin, null)

        val et_Nama = view.findViewById<EditText>(R.id.etNama)



        et_Nama.setText(vaksin.nama)


        builder.setView(view)

        builder.setPositiveButton("Update"){ p0 , p1 ->
            val dbVaksin = FirebaseDatabase.getInstance().getReference("Vaksin")
            val nama = et_Nama.text.toString().trim()
            if(nama.isEmpty()){
                et_Nama.error = "Mohon Nama Diisi"
                et_Nama.requestFocus()
                return@setPositiveButton
            }

            val vaksin = Vaksin(vaksin.id_v, nama)
            dbVaksin.child(vaksin.id_v!!).setValue(vaksin)

            Toast.makeText(mCtx,"Data Berhasil Di Update", Toast.LENGTH_SHORT).show()
        }
        builder.setNeutralButton("Delete"){p0,p1 ->
            val dbVaksin = FirebaseDatabase.getInstance().getReference("Vaksin").child(vaksin.id_v.toString())
            dbVaksin.removeValue()
            Toast.makeText(mCtx, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { p0, p1 ->

        }
        val alert = builder.create()
        alert.show()
    }
}