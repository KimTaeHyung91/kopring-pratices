package com.example.task.adapter.outbound.persistence

import com.example.task.adapter.outbound.persistence.entity.task.SubTaskEntity
import com.example.task.adapter.outbound.persistence.entity.task.TaskEntity
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository

interface TaskJpaRepository : JpaRepository<TaskEntity, Long>

interface SubTaskJpaRepository : JpaRepository<SubTaskEntity, String>, KotlinJdslJpqlExecutor
