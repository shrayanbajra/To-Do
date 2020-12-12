package com.example.todo.utils.helpers

import com.example.todo.db.task.TaskEntity
import com.example.todo.db.task.TaskStatus
import com.example.todo.utils.SortingOrder
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class TasksHelperTest {

    private val tab = "\t"
    private val newLine = "\n"

    val task1 = TaskEntity(
        title = "Apple",
        description = "Eat an apple",
        dateAdded = 100L,
        status = TaskStatus.NOT_DONE.value
    )

    val task2 = TaskEntity(
        title = "Cat",
        description = "Play with cat",
        dateAdded = 150L,
        status = TaskStatus.NOT_DONE.value
    )

    val task3 = TaskEntity(
        title = "Ball",
        description = "Play ball",
        dateAdded = 50L,
        status = TaskStatus.DONE.value
    )

    private lateinit var tasksHelper: TasksHelper

    @Before
    fun setUp() {
        tasksHelper = TasksHelper
    }

    @Test
    fun alphabetical_sorting_ascending_success_test() {

        // Arrange
        val tasks = arrayListOf(task1, task2, task3)
        val expectedResult = arrayListOf(task1, task3, task2)

        // Act
        val sortedTasks = tasksHelper.getAlphabeticallySortedTasks(
            tasks = tasks, sortingOrder = SortingOrder.VALUE_ASCENDING
        )

        // Assert
        print("Expected Result: ")
        expectedResult.forEach { print(it.title + tab) }

        print(newLine)
        print("Actual Result  : ")
        sortedTasks.forEach { print(it.title + tab) }

        assertThat(sortedTasks).isEqualTo(expectedResult)
        println(newLine)

    }

    @Test
    fun alphabetical_sorting_ascending_fail_test() {

        // Arrange
        val tasks = arrayListOf(task1, task2, task3)
        val expectedResult = arrayListOf(task1, task2, task3)

        // Act
        val sortedTasks = tasksHelper.getAlphabeticallySortedTasks(
            tasks = tasks, sortingOrder = SortingOrder.VALUE_ASCENDING
        )

        // Assert
        print("Expected Result: ")
        expectedResult.forEach { print(it.title + tab) }

        print("\nActual Result  : ")
        sortedTasks.forEach { print(it.title + tab) }

        assertThat(sortedTasks).isNotEqualTo(expectedResult)
        println(newLine)

    }

    @Test
    fun alphabetical_sorting_descending_success_test() {

        // Arrange
        val tasks = arrayListOf(task1, task2, task3)
        val expectedResult = arrayListOf(task2, task3, task1)

        // Act
        val sortedTasks = tasksHelper.getAlphabeticallySortedTasks(
            tasks = tasks, sortingOrder = SortingOrder.VALUE_DESCENDING
        )

        // Assert
        print("Expected Result: ")
        expectedResult.forEach { print(it.title + tab) }

        print(newLine)
        print("Actual Result  : ")
        sortedTasks.forEach { print(it.title + tab) }

        assertThat(sortedTasks).isEqualTo(expectedResult)
        println(newLine)

    }

    @Test
    fun alphabetical_sorting_descending_fail_test() {

        // Arrange
        val tasks = arrayListOf(task1, task2, task3)
        val expectedResult = arrayListOf(task1, task2, task3)

        // Act
        val sortedTasks = tasksHelper.getAlphabeticallySortedTasks(
            tasks = tasks, sortingOrder = SortingOrder.VALUE_DESCENDING
        )

        // Assert
        print("Expected Result: ")
        expectedResult.forEach { print(it.title + tab) }

        print(newLine)
        print("Actual Result  : ")
        sortedTasks.forEach { print(it.title + tab) }

        assertThat(sortedTasks).isNotEqualTo(expectedResult)
        println(newLine)

    }

    @Test
    fun completion_status_sorting_ascending_success_test() {

        // Arrange
        val tasks = arrayListOf(task2, task3)
        val expectedResult = arrayListOf(task2, task3)

        // Act
        val sortedTasks = tasksHelper.getTasksSortedByCompletionStatus(
            tasks = tasks, sortingOrder = SortingOrder.VALUE_ASCENDING
        )

        // Assert
        print("Expected Result: ")
        expectedResult.forEach {
            val taskStatus =
                if (it.isTaskDone()) "Done"
                else "Not Done"
            print(it.title + " Status: $taskStatus" + tab)
        }

        print(newLine)
        print("Actual Result  : ")
        sortedTasks.forEach {
            val taskStatus =
                if (it.isTaskDone()) "Done"
                else "Not Done"
            print(it.title + " Status: $taskStatus" + tab)
        }

        assertThat(sortedTasks).isEqualTo(expectedResult)
        println(newLine)

    }

    @Test
    fun completion_status_sorting_ascending_fail_test() {

        // Arrange
        val tasks = arrayListOf(task2, task3)
        val expectedResult = arrayListOf(task3, task2)

        // Act
        val sortedTasks = tasksHelper.getTasksSortedByCompletionStatus(
            tasks = tasks, sortingOrder = SortingOrder.VALUE_ASCENDING
        )

        // Assert
        print("Expected Result: ")
        expectedResult.forEach {
            val taskStatus =
                if (it.isTaskDone()) "Done"
                else "Not Done"
            print(it.title + " Status: $taskStatus" + tab)
        }

        print("\nActual Result  : ")
        sortedTasks.forEach {
            val taskStatus =
                if (it.isTaskDone()) "Done"
                else "Not Done"
            print(it.title + " Status: $taskStatus" + tab)
        }

        assertThat(sortedTasks).isNotEqualTo(expectedResult)
        println(newLine)

    }

    @Test
    fun completion_status_sorting_descending_success_test() {

        // Arrange
        val tasks = arrayListOf(task2, task3)
        val expectedResult = arrayListOf(task3, task2)

        // Act
        val sortedTasks = tasksHelper.getTasksSortedByCompletionStatus(
            tasks = tasks, sortingOrder = SortingOrder.VALUE_DESCENDING
        )

        // Assert
        print("Expected Result: ")
        expectedResult.forEach {
            val taskStatus =
                if (it.isTaskDone()) "Done"
                else "Not Done"
            print(it.title + " Status: $taskStatus" + tab)
        }

        print(newLine)
        print("Actual Result  : ")
        sortedTasks.forEach {
            val taskStatus =
                if (it.isTaskDone()) "Done"
                else "Not Done"
            print(it.title + " Status: $taskStatus" + tab)
        }

        assertThat(sortedTasks).isEqualTo(expectedResult)
        println(newLine)

    }

    @Test
    fun completion_status_sorting_descending_fail_test() {

        // Arrange
        val tasks = arrayListOf(task2, task3)
        val expectedResult = arrayListOf(task2, task3)

        // Act
        val sortedTasks = tasksHelper.getTasksSortedByCompletionStatus(
            tasks = tasks, sortingOrder = SortingOrder.VALUE_DESCENDING
        )

        // Assert
        print("Expected Result: ")
        expectedResult.forEach {
            val taskStatus =
                if (it.isTaskDone()) "Done"
                else "Not Done"
            print(it.title + " Status: $taskStatus" + tab)
        }

        print(newLine)
        print("Actual Result  : ")
        sortedTasks.forEach {
            val taskStatus =
                if (it.isTaskDone()) "Done"
                else "Not Done"
            print(it.title + " Status: $taskStatus" + tab)
        }

        assertThat(sortedTasks).isNotEqualTo(expectedResult)
        println(newLine)

    }

    @Test
    fun date_added_sorting_ascending_success_test() {

        // Arrange
        val tasks = arrayListOf(task2, task1, task3)
        val expectedResult = arrayListOf(task3, task1, task2)

        // Act
        val sortedTasks = tasksHelper.getTasksSortedByDateAdded(
            tasks = tasks, sortingOrder = SortingOrder.VALUE_ASCENDING
        )

        // Assert
        print("Expected Result: ")
        expectedResult.forEach { print(it.title + " Date Added: ${it.dateAdded}" + tab) }

        print(newLine)
        print("Actual Result  : ")
        sortedTasks.forEach { print(it.title + " Date Added: ${it.dateAdded}" + tab) }

        assertThat(sortedTasks).isEqualTo(expectedResult)
        println(newLine)

    }

    @Test
    fun date_added_sorting_ascending_fail_test() {

        // Arrange
        val tasks = arrayListOf(task2, task1, task3)
        val expectedResult = arrayListOf(task3, task2, task1)

        // Act
        val sortedTasks = tasksHelper.getTasksSortedByDateAdded(
            tasks = tasks, sortingOrder = SortingOrder.VALUE_ASCENDING
        )

        // Assert
        print("Expected Result: ")
        expectedResult.forEach { print(it.title + " Date Added: ${it.dateAdded}" + tab) }

        print("\nActual Result  : ")
        sortedTasks.forEach { print(it.title + " Date Added: ${it.dateAdded}" + tab) }

        assertThat(sortedTasks).isNotEqualTo(expectedResult)
        println(newLine)

    }

    @Test
    fun date_added_sorting_descending_success_test() {

        // Arrange
        val tasks = arrayListOf(task2, task1, task3)
        val expectedResult = arrayListOf(task2, task1, task3)

        // Act
        val sortedTasks = tasksHelper.getTasksSortedByDateAdded(
            tasks = tasks, sortingOrder = SortingOrder.VALUE_DESCENDING
        )

        // Assert
        print("Expected Result: ")
        expectedResult.forEach { print(it.title + " Date Added: ${it.dateAdded}" + tab) }

        print(newLine)
        print("Actual Result  : ")
        sortedTasks.forEach { print(it.title + " Date Added: ${it.dateAdded}" + tab) }

        assertThat(sortedTasks).isEqualTo(expectedResult)
        println(newLine)

    }

    @Test
    fun date_added_sorting_descending_fail_test() {

        // Arrange
        val tasks = arrayListOf(task2, task1, task3)
        val expectedResult = arrayListOf(task2, task3, task1)

        // Act
        val sortedTasks = tasksHelper.getTasksSortedByDateAdded(
            tasks = tasks, sortingOrder = SortingOrder.VALUE_DESCENDING
        )

        // Assert
        print("Expected Result: ")
        expectedResult.forEach { print(it.title + " Date Added: ${it.dateAdded}" + tab) }

        print(newLine)
        print("Actual Result  : ")
        sortedTasks.forEach { print(it.title + " Date Added: ${it.dateAdded}" + tab) }

        assertThat(sortedTasks).isNotEqualTo(expectedResult)
        println(newLine)

    }

}