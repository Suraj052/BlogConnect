package com.example.blog.Models


data class UserDataModel(
    val about: String,
    val blog_posts: List<Post>,
    val id: Int,
    val email: String,
    val first_name: String,
    val user_name: String
)

