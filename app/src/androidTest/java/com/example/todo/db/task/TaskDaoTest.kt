package com.example.todo.db.task

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.todo.db.TodoDatabase
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class TaskDaoTest {

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

}