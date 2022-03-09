package com.alex.firebaseauth

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class User_vaksin_rs_adapter(val mCtx : Context, val layoutResId: Int, val rsList : List<Vaksin>): ArrayAdapter<Vaksin>(mCtx, layoutResId,
    rsList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)

        val view : View = layoutInflater.inflate(layoutResId, null)

        val tv_Nama : TextView = view.findViewById(R.id.tvNama)

        val rs = rsList[position]

        tv_Nama.text = rs.nama

        return view
    }
}