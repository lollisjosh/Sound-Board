package com.example.soundboard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class OAuthLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authorizationUrl = OAuthService.getAuthorizationUrl()
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(authorizationUrl))
        startActivity(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val uri = intent?.data
        if (uri != null && uri.toString().startsWith(OAuthService.CALLBACK_URL)) {
            val code = uri.getQueryParameter("code")
            if (code != null) {
                val accessToken = OAuthService.getAccessToken(code)
                // Save accessToken for future use
            }
        }
    }
}