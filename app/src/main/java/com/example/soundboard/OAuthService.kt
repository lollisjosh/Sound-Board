package com.example.soundboard

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.github.scribejava.core.builder.ServiceBuilder
import com.github.scribejava.core.model.OAuth2AccessToken
import com.github.scribejava.core.oauth.OAuth20Service

// OAuthService.kt
object OAuthService {
    private const val CLIENT_ID = "YOUR_CLIENT_ID"
    private const val CLIENT_SECRET = "YOUR_CLIENT_SECRET"
    private const val CALLBACK_URL = "YOUR_CALLBACK_URL"

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
}