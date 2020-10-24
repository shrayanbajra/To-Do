package com.example.todo.di.navhost

import androidx.lifecycle.ViewModel
import com.example.todo.di.app.ViewModelKey
import com.example.todo.ui.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class NavHostViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

}