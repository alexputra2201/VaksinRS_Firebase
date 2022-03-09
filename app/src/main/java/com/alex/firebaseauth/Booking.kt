package com.alex.firebaseauth

import java.sql.Time

data class Booking (
    val id_b : String?,
    val waktu : String,
    val nama : String
){
    constructor(): this("","",""){

    }
}