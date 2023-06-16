package com.example.blog.Models

data class PostRequest(
    val title: String,
    val category: String?,
    val author: String,
    val content: String,
    val imageUrl: String,

    )

data class PostResponse(
    val author: Int,
    val category: Int,
    val content: String,
    val id: Int,
    val imageUrl: String,
    val published: String,
    val title: String
)