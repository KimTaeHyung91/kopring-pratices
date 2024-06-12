package com.example.task.adapter.outbound.persistence.entity.task

import com.example.task.domain.task.Task
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "task")
class TaskEntity(
    name: String,
    description: String?,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column
    var name: String = name
        protected set

    @Column(nullable = true)
    var description: String? = description
        protected set

    fun toDomain(): Task = Task(id = this.id!!, name = this.name, description = this.description)

    fun update(task: Task) {
        this.name = task.name

        if (task.description != null) {
            this.description = task.description
        }
    }
}
