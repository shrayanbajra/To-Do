package com.example.todo.data

data class Criteria(
    var isSelected: Boolean = false,
    var iconResource: Int,
    var title: String
)