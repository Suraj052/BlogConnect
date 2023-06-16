package com.example.blog.Models

data class UserResponse(
    val about: String,
    val email: String,
    val first_name: String,
    val user_name: String
)

data class UserRequest(
    val about: String,
    val email: String,
    val first_name: String,
    val user_name: String,
    val password : String
)