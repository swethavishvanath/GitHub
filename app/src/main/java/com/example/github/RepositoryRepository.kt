package com.example.github

class RepositoryRepository(private val githubService: GitHubService, private val repositoryDao: RepositoryDao){

    suspend fun getUserRepositories(username: String): List<Repository> {
        // Fetch repositories from API
        val repositories = githubService.getUserRepositories(username)
        // Cache repositories locally
        repositoryDao.insertAll(repositories)
        return repositories
    }

    suspend fun searchRepositories(query: String): List<Repository> {
        // Fetch repositories from API
        val searchResponse = githubService.searchRepositories1(query)
        // Cache search results locally
        searchResponse.items.forEach { repositoryDao.insert(it) }
        return searchResponse.items
    }

    suspend fun getAllRepositories(): List<Repository> {
        // Get cached repositories from local database
        return repositoryDao.getAll()
    }
    suspend fun searchRepositories2(query: String): List<Repository> {
        val cachedRepositories = repositoryDao.getAllRepositories()
        if (cachedRepositories.isNotEmpty()) {
            return cachedRepositories.map { cachedRepo ->
                Repository(cachedRepo.id, cachedRepo.name, cachedRepo.description,cachedRepo.stars,cachedRepo.forks)
            }
        }

        val response = githubService.searchRepositories2(query)
        if (response.isSuccessful) {
            val repositories = response.body()?.items ?: emptyList()
            repositoryDao.insertRepositories(repositories.map { cachedRepo ->
                CachedRepository(cachedRepo.id, cachedRepo.name, cachedRepo.description,cachedRepo.stars,cachedRepo.forks)
            })
            return repositories
        }
        else {
            throw Exception("failed")
        }
    }
}