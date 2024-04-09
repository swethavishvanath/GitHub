package com.example.github
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object AppState {
    // MutableState to track if a new message has been received
    var newMessageReceived by mutableStateOf(false)
}