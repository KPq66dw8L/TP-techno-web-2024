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
    val id: UUID? = null,
    val username: String,
    val email: String,
    val password: String
) {
    // No-argument constructor required by Hibernate
    constructor() : this(null, "", "", "")
}