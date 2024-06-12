package com.example.task.domain.task

class Task(val id: Long = 0, var name: String, var description: String?) {
    fun modify(
        name: String,
        description: String?,
    ) {
        this.name = name
        this.description = description
    }
}
