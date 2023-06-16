package com.example.blog.Models

data class BlogPost(
    val author: Int,
    val category: Int,
    val content: String,
    val id: Int,
    val imageUrl: String,
    val published: String,
    val title: String
)