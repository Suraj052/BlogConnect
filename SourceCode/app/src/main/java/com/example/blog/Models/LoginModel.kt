package com.example.blog.Models

data class LoginResponse(
    val access: String,
    val refresh: String
)

data class LoginRequest(
    val email: String,
    val password : String
)