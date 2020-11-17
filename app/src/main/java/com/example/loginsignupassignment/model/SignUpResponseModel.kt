package com.example.loginsignupassignment.model

data class SignUpResponseModel(
    var token: String? = null,
    var statusCode: String? = null,
    var message: String? = null,
    var error: String? = null
)