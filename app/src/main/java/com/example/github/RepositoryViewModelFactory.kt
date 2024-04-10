package com.example.github

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.github.RepositoryRepository
import com.example.github.RepositoryViewModel
class RepositoryViewModelFactory(private val repositoryRepository: RepositoryRepository) : ViewModelProvider.Factory {
     override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepositoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RepositoryViewModel(repositoryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}