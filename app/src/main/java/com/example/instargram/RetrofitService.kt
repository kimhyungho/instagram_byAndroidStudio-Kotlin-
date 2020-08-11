package com.example.instargram

import android.media.MediaMetadataRetriever
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @POST("user/signup/")
    @FormUrlEncoded
    fun register(
        @Field("username") username: String,
        @Field("password1") password1: String,
        @Field("password2") password2: String
    ): Call<User>                       // register 의 내용으로 요청을 하면 User 형식으로 답이 옴

    @POST("user/login/")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<User>

    @GET("instagram/post/list/all/")
    fun getAllPost(): Call<ArrayList<Post>>

    @Multipart
    @POST("instagram/post/")
    fun uploadPost(
        @Part image: MultipartBody.Part,
        @Part("content") requestBody: RequestBody
    ): Call<Post>

    @GET("instagram/post/list")
    fun getUserPostList(): Call<ArrayList<Post>>

}