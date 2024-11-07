package com.thedonorzone.thedonorzone.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    val id: UUID? = null,

    @Column(unique = true, length = 100, nullable = false)
    val username: String,

    @Column(unique = true, length = 100, nullable = false)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    val createdAt: Date? = null,

    @UpdateTimestamp
    @Column(name = "updated_at")
    val updatedAt: Date? = null
)