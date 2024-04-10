package com.example.github
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepositoryViewModel(private val repositoryRepository: RepositoryRepository) : ViewModel() {

    private val _repositories = MutableLiveData<List<Repository>>()
    val repositories: LiveData<List<Repository>> get() = _repositories
    fun getUserRepositories(username: String) {
        viewModelScope.launch {
            _repositories.value = repositoryRepository.getUserRepositories(username)
        }
    }

    fun searchRepositories(query: String) {
        viewModelScope.launch {
            _repositories.value = repositoryRepository.searchRepositories(query)
        }
    }

    fun getAllRepositories() {
        viewModelScope.launch {
            _repositories.value = repositoryRepository.getAllRepositories()
        }
    }
    private val _repositories1 = mutableStateOf<List<Repository>>(emptyList())
    val repositories1: State<List<Repository>> = _repositories1

    fun searchRepositories2(query: String) {
        viewModelScope.launch {
            _repositories1.value = repositoryRepository.searchRepositories2(query)
        }
    }
}