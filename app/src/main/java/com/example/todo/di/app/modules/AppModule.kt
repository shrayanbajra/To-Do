package com.example.todo.di.app.modules

import android.app.Application
import androidx.room.Room
import com.example.todo.db.TodoDatabase
import com.example.todo.db.task.TaskDao
import com.example.todo.utils.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideTodoDatabase(application: Application): TodoDatabase {
        return Room.databaseBuilder(
            application, TodoDatabase::class.java, Constants.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideTaskDao(database: TodoDatabase): TaskDao {
        return database.taskDao()
    }

}