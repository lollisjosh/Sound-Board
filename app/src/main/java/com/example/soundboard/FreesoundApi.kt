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
import retrofit2.http.Body

interface FreesoundApi {
    @Headers("Authorization: Token JqN1s6a8zzghshW402DRj6acgJ6BLZQLJA5y4oJK")
    @GET("sounds/{id}/")
    fun getSoundById(@Path("id") id: Int): Call<Sound>

    @Multipart
    @POST("sounds/upload/")
    fun uploadSound(
        @Part audioFile: MultipartBody.Part,
        @Part("name") name: RequestBody?,
        @Part("tags") tags: RequestBody,
        @Part("description") description: RequestBody,
        @Part("license") license: RequestBody = OAuthService.createRequestBody("Attribution"),
        @Part("pack") pack: RequestBody? = null,
        @Part("geotag") geotag: RequestBody? = null
    ): Call<UploadResponse>

    @POST("sounds/{id}/edit/")
    fun editSound(
        @Path("id") id: Int,
        @Body description: Map<String, String>
    ): Call<SoundDescriptionResponse>
}
