package com.example.todo.di

import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideSomeString() = "Dagger is working"

}