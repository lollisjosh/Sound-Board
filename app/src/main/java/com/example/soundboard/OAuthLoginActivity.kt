package com.example.soundboard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class OAuthLoginActivity : AppCompatActivity() {
    private val TAG = "OAuthLoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authorizationUrl = OAuthService.getAuthorizationUrl()
        Log.d(TAG, "Starting OAuth flow with URL: $authorizationUrl")
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(authorizationUrl))
        startActivity(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d(TAG, "Received callback intent: ${intent.data}")
        
        val uri = intent.data
        if (uri != null && uri.scheme == "soundboard" && uri.host == "oauth-callback") {
            val code = uri.getQueryParameter("code")
            if (code != null) {
                try {
                    val accessToken = OAuthService.getAccessToken(code)
                    AccessTokenManager.saveAccessToken(accessToken.accessToken)
                    
                    // Return to MainActivity
                    val mainIntent = Intent(this, MainActivity::class.java)
                    mainIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(mainIntent)
                    finish()
                } catch (e: Exception) {
                    Log.e(TAG, "Error getting access token", e)
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.e(TAG, "No code in callback")
                Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}