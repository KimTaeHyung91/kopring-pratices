package com.example.task.domain.task

import java.time.LocalDateTime

class SubTask(
    val id: String? = null,
    reporter: String,
    name: String,
    description: String?,
    assigner: String?,
    val createdAt: LocalDateTime? = null,
) {
    var name = name
        private set

    var description = description
        private set

    var assigner = assigner
        private set

    var reporter = reporter
        private set

    fun modifyInfo(
        name: String?,
        description: String?,
        assigner: String?,
    ) {
        if (name != null) {
            this.name = name
        }

        this.description = description
        this.assigner = assigner
    }
}
