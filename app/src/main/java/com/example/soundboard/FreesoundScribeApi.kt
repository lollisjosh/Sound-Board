package com.example.soundboard

import com.github.scribejava.core.builder.api.DefaultApi20

class FreesoundScribeApi : DefaultApi20() {
    companion object {
        private const val AUTHORIZE_URL = "https://freesound.org/apiv2/oauth2/authorize"
        private const val TOKEN_URL = "https://freesound.org/apiv2/oauth2/access_token"

        fun instance(): FreesoundScribeApi {
            return FreesoundScribeApi()
        }
    }

    override fun getAccessTokenEndpoint(): String {
        return TOKEN_URL
    }

    override fun getAuthorizationBaseUrl(): String {
        return AUTHORIZE_URL
    }
}