package com.alex.firebaseauth

data class Vaksin (
    val id_v : String?,
    val nama : String
) {
    constructor() : this("", "") {

    }
}