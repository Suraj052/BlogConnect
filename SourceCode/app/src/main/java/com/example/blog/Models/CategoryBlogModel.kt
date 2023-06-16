package com.example.blog.Models


data class CategoryBlogModel(
    val id: Int,
    val name: String,
    val posts: List<Post>
)

data class Post(
    val author: String,
    val category: String,
    val content: String,
    val id: Int,
    val imageUrl: String,
    val published: String,
    val title: String
)