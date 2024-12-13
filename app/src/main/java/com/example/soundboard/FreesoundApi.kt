package com.example.soundboard

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Multipart
import retrofit2.http.Part
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface FreesoundApi {
    @Headers("Authorization: Token JqN1s6a8zzghshW402DRj6acgJ6BLZQLJA5y4oJK")
    @GET("sounds/{id}/")
    fun getSoundById(@Path("id") id: Int): Call<Sound>

    @Multipart
    @POST("sounds/upload/")
    fun uploadSound(
        @Part audioFile: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("tags") tags: RequestBody
    ): Call<Sound>
}