package com.example.github

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [CachedRepository::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao
}