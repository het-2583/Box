package com.example.box

class database {
    data class SignIn(
        val uid:String="",
        val name: String = "",
        val email: String = "",
        val password: String = "",
        val isAdmin: Int
    )
}