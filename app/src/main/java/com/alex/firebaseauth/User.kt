package com.alex.firebaseauth

data class User(
    val id : String?,
    val username : String,
    val password : String,
){
    constructor(): this("","","",){

    }
}