package com.example.github

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun RepositoryListScreen(viewModel: RepositoryViewModel) {
    val repositories by viewModel.repositories.observeAsState(listOf())
    SearchScreen(viewModel)
    LazyColumn {
        items(repositories) { repository ->
            RepositoryItem(repository)
        }
    }
}