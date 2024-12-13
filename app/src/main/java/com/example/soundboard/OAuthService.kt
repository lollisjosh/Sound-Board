package com.example.soundboard

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.github.scribejava.core.builder.ServiceBuilder
import com.github.scribejava.core.model.OAuth2AccessToken
import com.github.scribejava.core.oauth.OAuth20Service
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

object OAuthService {
    private const val CLIENT_ID = "t1L5gQX2SkwRnmSmCorK"
    private const val CLIENT_SECRET = "JqN1s6a8zzghshW402DRj6acgJ6BLZQLJA5y4oJK"
    private const val SERVER_URL = "https://sound-board-production-ad2a.up.railway.app"
    private const val REDIRECT_URL = "$SERVER_URL/callback"

    fun getCallbackUrl(): String = REDIRECT_URL
    
    // Direct to server auth endpoint
    fun getAuthorizationUrl(): String = "$SERVER_URL/auth"
    
    fun getCurrentAccessToken(): String? = AccessTokenManager.getAccessToken()
    
    fun getAccessToken(code: String): OAuth2AccessToken {
        return service.getAccessToken(code)
    }

    fun createRequestBody(content: String): RequestBody {
        return content.toRequestBody("text/plain".toMediaType())
    }

    fun createMultipartBody(file: File, paramName: String): MultipartBody.Part {
        val requestFile = file.readBytes().toRequestBody("audio/*".toMediaType())
        return MultipartBody.Part.createFormData(paramName, file.name, requestFile)
    }
    
    private val service: OAuth20Service = ServiceBuilder(CLIENT_ID)
        .apiSecret(CLIENT_SECRET)
        .callback(REDIRECT_URL)
        .build(FreesoundScribeApi.instance())
}