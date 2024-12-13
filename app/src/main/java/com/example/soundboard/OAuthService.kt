package com.example.soundboard

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.github.scribejava.core.builder.ServiceBuilder
import com.github.scribejava.core.model.OAuth2AccessToken
import com.github.scribejava.core.oauth.OAuth20Service
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

object OAuthService {
    private const val CLIENT_ID = "t1L5gQX2SkwRnmSmCorK"
    private const val CLIENT_SECRET = "JqN1s6a8zzghshW402DRj6acgJ6BLZQLJA5y4oJK"
    private const val CALLBACK_URL = "soundboard://oauth-callback"

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

    fun createRequestBody(content: String): okhttp3.RequestBody {
        return content.toRequestBody("text/plain".toMediaType())
    }

    fun createMultipartBody(file: File, paramName: String): MultipartBody.Part {
        val requestFile = file.readBytes().toRequestBody("audio/*".toMediaType())
        return MultipartBody.Part.createFormData(paramName, file.name, requestFile)
    }
}