package com.example.github

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {
    @GET("users/{username}/repos")
    suspend fun getUserRepositories(@Path("username") username: String): List<Repository>
    @GET("search/repositories")
    suspend fun searchRepositories2(@Query("q") query: String): Response<RepositorySearchResponse>
    @GET("search/repositories")
    suspend fun searchRepositories1(@Query("q") query: String): RepositorySearchResponse
}