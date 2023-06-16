package com.example.blog.Retrofit
import com.example.blog.Models.*
import retrofit2.Response
import retrofit2.http.*

interface API {

    @GET("api/posts/")
    suspend fun getPosts(): Response<List<PostGetModel>>

    @GET("api/category/Fashion")
    suspend fun getCategoryFashion(): Response<CategoryBlogModel>

    @GET("api/category/Food")
    suspend fun getCategoryFood(): Response<CategoryBlogModel>

    @GET("api/category/Health")
    suspend fun getCategoryHealth(): Response<CategoryBlogModel>

    @GET("api/category/Travel")
    suspend fun getCategoryTravel(): Response<CategoryBlogModel>

    @POST("api/posts/")
    fun postBlog(@Body postRequest: PostRequest): retrofit2.Call<PostResponse>

    @POST("api/user/register/")
    fun registerUser(@Body userRequest: UserRequest): retrofit2.Call<UserResponse>

    @POST("token/")
    fun loginUser(@Body loginRequest: LoginRequest): retrofit2.Call<LoginResponse>

    @GET("api/user/userlist")
    suspend fun userData(@Header("Authorization") authHeader: String) : Response<List<UserDataModel>>



}