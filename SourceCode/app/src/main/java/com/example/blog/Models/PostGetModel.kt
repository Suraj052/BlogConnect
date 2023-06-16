package com.example.blog.Models

data class PostGetModel(
    val author: String,
    val category: String,
    val content: String,
    val id: Int,
    val imageUrl: String,
    val published: String,
    val title: String
)