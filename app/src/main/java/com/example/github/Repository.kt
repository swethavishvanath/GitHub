package com.example.github
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "repositories")
data class Repository(
    @PrimaryKey
    val id: Long,
    val name: String,
    val description: String?,
    val stars:String,
    val forks:String
)
