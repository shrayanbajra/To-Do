package com.example.todo.db.task

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.todo.db.TodoDatabase
import com.example.todo.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TaskDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: TodoDatabase
    private lateinit var taskDao: TaskDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), TodoDatabase::class.java
        ).allowMainThreadQueries().build()
        taskDao = database.taskDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insert() = runBlockingTest {

        val task = TaskEntity(
            status = TaskStatus.NOT_DONE.value,
            title = "Learn testing",
            description = "Learn unit testing"
        )
        taskDao.insert(task)

        val allTasks = taskDao.getAll().getOrAwaitValue()

        assertThat(allTasks).contains(task)

    }

    @Test
    fun update() = runBlockingTest {

        val commonId = 10

        val task = TaskEntity(
            status = TaskStatus.NOT_DONE.value,
            title = "Learn testing",
            description = "Learn unit testing"
        )
        task.id = commonId
        taskDao.insert(task)

        val modifiedTask = TaskEntity(
            status = TaskStatus.DONE.value,
            title = "Learn testing",
            description = "Learn unit testing and integration testing"
        )
        modifiedTask.id = commonId
        taskDao.update(modifiedTask)

        val retrievedTask = taskDao.get(commonId)

        assertThat(retrievedTask).isEqualTo(modifiedTask)

    }

    @Test
    fun get() = runBlockingTest {

        val taskId = 10
        val task = TaskEntity(
            status = TaskStatus.NOT_DONE.value,
            title = "Learn testing",
            description = "Learn unit testing"
        )
        task.id = taskId
        taskDao.insert(task)

        val retrievedTask = taskDao.get(taskId)

        assertThat(retrievedTask).isEqualTo(task)

    }

    @Test
    fun getAll() = runBlockingTest {

        val firstId = 1
        val task1 = TaskEntity(
            status = TaskStatus.NOT_DONE.value,
            title = "Learn testing",
            description = "Learn unit testing"
        )
        task1.id = firstId
        taskDao.insert(task1)

        val secondId = 2
        val task2 = TaskEntity(
            status = TaskStatus.NOT_DONE.value,
            title = "Learn testing",
            description = "Learn unit testing"
        )
        task2.id = secondId
        taskDao.insert(task2)

        val allTasks = taskDao.getAll().getOrAwaitValue().toMutableList()
        allTasks.remove(task1)
        allTasks.remove(task2)

        assertThat(allTasks).isEmpty()

    }

    @Test
    fun delete() = runBlockingTest {

        val task = TaskEntity(
            status = TaskStatus.NOT_DONE.value,
            title = "Learn testing",
            description = "Learn unit testing"
        )
        task.id = 1
        taskDao.insert(task)

        taskDao.delete(task.id)

        val allTasks = taskDao.getAll().getOrAwaitValue()

        assertThat(allTasks).doesNotContain(task)

    }

}