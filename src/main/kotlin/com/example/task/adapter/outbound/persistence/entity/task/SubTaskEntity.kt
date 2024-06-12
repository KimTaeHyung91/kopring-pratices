package com.example.task.adapter.outbound.persistence.entity.task

import com.example.task.adapter.outbound.persistence.entity.BaseEntity
import com.example.task.domain.task.SubTask
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

/**
 * subtask 입장에서 적어도 하나의 task는 존재햐야되서 생성자로 받아버림
 * lateinit 객체 생성 시 초기화 되어있지 않기때문에 문제, 조회 시에는 jpa 가 알아서 초기화 해주기 때문에 문제없어보임.
 */

@Entity
@Table(name = "sub_task")
class SubTaskEntity(
    name: String,
    description: String? = null,
    assigner: String? = null,
    reporter: String,
    taskEntity: TaskEntity,
) : BaseEntity() {
    @Column
    var name: String = name
        protected set

    @Column(nullable = true)
    var description: String? = description
        protected set

    @Column(nullable = true)
    var assigner: String? = assigner
        protected set

    @Column
    var reporter: String = reporter
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    var taskEntity: TaskEntity = taskEntity
        protected set

    fun toDomain() = SubTask(name = name, description = description, assigner = assigner, reporter = reporter)

    fun update(subTask: SubTask) {
        name = subTask.name

        if (subTask.description != null) {
            description = subTask.description
        }

        if (subTask.assigner != null) {
            assigner = subTask.assigner
        }
    }

    override fun toString(): String {
        return "SubTaskEntity(id =$id, name = $name)"
    }
}
