package com.example.todo.di.navhost

import androidx.lifecycle.ViewModel
import com.example.todo.di.app.utils.ViewModelKey
import com.example.todo.ui.add_task.AddTaskViewModel
import com.example.todo.ui.my_tasks.MyTasksViewModel
import com.example.todo.ui.view_task.ViewTaskViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class NavHostViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MyTasksViewModel::class)
    abstract fun bindMyTasksViewModel(viewModel: MyTasksViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ViewTaskViewModel::class)
    abstract fun bindViewTaskViewModel(viewModel: ViewTaskViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddTaskViewModel::class)
    abstract fun bindAddTaskViewModel(viewModel: AddTaskViewModel): ViewModel

}