package com.example.github

import android.app.Application
import androidx.room.Room
import com.example.github.AppDatabase

class MyClass : Application() {

    companion object {
        lateinit var database: AppDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()

        // Initialize Room database instance
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "repository-db"
        ).build()
    }
}