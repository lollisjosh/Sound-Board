package com.example.soundboard

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.github.scribejava.core.builder.ServiceBuilder
import com.github.scribejava.core.model.OAuth2AccessToken
import com.github.scribejava.core.oauth.OAuth20Service
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

// OAuthService.kt
object OAuthService {
    private const val CLIENT_ID = "t1L5gQX2SkwRnmSmCorK"  // From Freesound API credentials
    private const val CLIENT_SECRET = "JqN1s6a8zzghshW402DRj6acgJ6BLZQLJA5y4oJK"   // From Freesound API credentials
    private const val CALLBACK_URL = "http://freesound.org/home/app_permissions/permission_granted/"  // Your app's scheme

    // Add public getter
    fun getCallbackUrl(): String = CALLBACK_URL

    private val service: OAuth20Service = ServiceBuilder(CLIENT_ID)
        .apiSecret(CLIENT_SECRET)
        .callback(CALLBACK_URL)
        .build(FreesoundScribeApi.instance())

    fun getAuthorizationUrl(): String {
        return service.authorizationUrl
    }

    fun getAccessToken(code: String): OAuth2AccessToken {
        return service.getAccessToken(code)
    }

    fun getCurrentAccessToken(): String? {
        return AccessTokenManager.getAccessToken()
    }

    fun refreshAccessToken(refreshToken: String): OAuth2AccessToken {
        return service.refreshAccessToken(refreshToken)
    }

    fun createRequestBody(content: String): RequestBody {
        return RequestBody.create(MediaType.parse("text/plain"), content)
    }

    fun createMultipartBody(file: File, paramName: String): MultipartBody.Part {
        val requestFile = RequestBody.create(MediaType.parse("audio/*"), file)
        return MultipartBody.Part.createFormData(paramName, file.name, requestFile)
    }
}