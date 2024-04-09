package com.example.github

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.github.RepositoryViewModel

@Composable
fun SearchScreen(viewModel: RepositoryViewModel) {
    var query by remember { mutableStateOf(TextFieldValue()) }
    val context = LocalContext.current
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.activeNetwork ?: return
    val networkInfo = connectivityManager.getNetworkCapabilities(networkCapabilities)

    val isConnected = networkInfo?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    Column {
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search") },
            singleLine = true,
            modifier = Modifier.padding(16.dp)
        )

        Button(
            onClick = { if(isConnected==true) {
                viewModel.searchRepositories(query.text)
            }else{
                viewModel.searchRepositories2(query.text)
            }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Search")
        }
    }
}