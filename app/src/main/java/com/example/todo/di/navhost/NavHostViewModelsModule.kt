package com.example.todo.di.navhost

import androidx.lifecycle.ViewModel
import com.example.todo.di.app.utils.ViewModelKey
import com.example.todo.ui.addtask.AddTaskViewModel
import com.example.todo.ui.home.HomeViewModel
import com.example.todo.ui.viewtask.ViewTaskViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class NavHostViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ViewTaskViewModel::class)
    abstract fun bindViewTaskViewModel(viewModel: ViewTaskViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddTaskViewModel::class)
    abstract fun bindAddTaskViewModel(viewModel: AddTaskViewModel): ViewModel

}