package com.example.todo.utils

object Constants {

    const val DATABASE_NAME = "todo_database"

    /**
     * Pref
     */
    const val PREF_MY_TASKS = "pref_my_tasks"

    /**
     * Key: Sorting Criteria
     */
    const val KEY_SORTING_CRITERIA = "key_sorting_criteria"

    enum class SortingCriteria(val value: String) {
        VALUE_ALPHABETICALLY("value_alphabetically"),
        VALUE_COMPLETED("value_completed")
    }

    /**
     * Key: Sorting Order
     */
    const val KEY_SORTING_ORDER = "key_sorting_order"

    enum class SortingOrder(val value: String) {
        VALUE_ASCENDING("value_ascending"),
        VALUE_DESCENDING("value_descending")
    }

    /**
     * Status
     */
    const val STATUS_NOT_DONE = 0
    const val STATUS_DONE = 1

    /**
     * Misc
     */
    const val NO_TASK_ID = -1
    const val EMPTY_STRING = ""

}