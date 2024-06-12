package com.example.task.adapter.outbound.persistence.entity

import com.example.task.common.ulid.getULID
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PostLoad
import jakarta.persistence.PostPersist
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.domain.Persistable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity : Persistable<String> {
    @Id
    @Column(name = "id")
    private val ulid: String = getULID()

    @CreationTimestamp
    var createdAt: LocalDateTime? = null

    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null

    @Transient
    private var isNew = (createdAt == null)

    override fun isNew(): Boolean = isNew

    override fun getId(): String = ulid

    @PostLoad
    @PostPersist
    fun markNotNew() {
        this.isNew = false
    }
}
