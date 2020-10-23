package com.example.todo.di

import android.app.Application
import androidx.room.Room
import com.example.todo.app.Constants
import com.example.todo.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideTodoDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application, AppDatabase::class.java, Constants.DATABASE_NAME
        ).build()
    }

}