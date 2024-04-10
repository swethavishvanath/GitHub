package com.example.github

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.messaging.FirebaseMessaging

@Composable
fun MyApp() {
    val context = LocalContext.current

    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val token = task.result
            Log.d("FCM Token", token ?: "Token retrieval failed")
        } else {
            Log.e("FCM Token", "Token retrieval failed")
        }
    }
}
