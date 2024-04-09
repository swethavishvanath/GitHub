package com.example.github

data class Repository(
    val id: Long,
    val name: String,
    val description: String?,
    val stars:String,
    val forks:String
)