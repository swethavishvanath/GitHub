package com.example.github

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle incoming message
        Log.d(TAG, "Message data payload: ${remoteMessage.data}")

        // Update UI based on the received message
        AppState.newMessageReceived = true
    }

    companion object {
        private const val TAG = "MyFirebaseMessagingServ"
    }
}