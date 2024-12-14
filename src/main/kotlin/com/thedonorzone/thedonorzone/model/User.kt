package com.thedonorzone.thedonorzone.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var username: String? = null,
    @Column(name = "email", unique = true, nullable = false)
    val email: String? = null,
    var password: String,

    @Column(name = "created_at", updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    // No-argument constructor required by Hibernate
    constructor() : this(null, null, null, "")
    fun updatePassword(newPassword: String) {
        this.password = newPassword
    }
}
