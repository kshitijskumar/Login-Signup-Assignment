package com.example.loginsignupassignment.model

import com.google.gson.annotations.SerializedName

data class LoginResponseModel(
    var id: String? = null,
    var name: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var country: String? = null,
    var city: String? = null,
    @SerializedName("user_type")
    var userType: String? = null,
    var token: String? = null
)