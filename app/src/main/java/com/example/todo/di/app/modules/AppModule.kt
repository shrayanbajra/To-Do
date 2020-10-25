package com.example.todo.di.app.modules

import android.app.Application
import androidx.room.Room
import com.example.todo.db.AppDatabase
import com.example.todo.db.task.TaskDao
import com.example.todo.utils.Constants
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

    @Singleton
    @Provides
    fun provideTaskDao(database: AppDatabase): TaskDao {
        return database.taskDao()
    }

    @Singleton
    @Provides
    fun provideSomeString() = "Dagger is working properly"

}