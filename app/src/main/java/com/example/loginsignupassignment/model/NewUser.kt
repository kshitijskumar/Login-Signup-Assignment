package com.example.loginsignupassignment.model

data class NewUser(
    val name: String,
    val phone: String,
    val email: String,
    val password: String,
    val country: String,
    val city: String
)