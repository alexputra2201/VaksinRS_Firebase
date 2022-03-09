package com.alex.firebaseauth

data class RS (
    val id : String?,
    val nama : String,
    val alamat : String,
    val latitude : String,
    val longitude : String
){
    constructor(): this("","","","",""){

    }
}