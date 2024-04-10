package com.example.github

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query



@Dao
interface RepositoryDao {
    @Query("SELECT * FROM repositories")
    suspend fun getAllRepositories(): List<CachedRepository>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(repositories: List<CachedRepository>)

    @Query("SELECT * FROM repositories")
    suspend fun getAll(): List<Repository>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repositories: List<Repository>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(it: Repository)

}