package com.thedonorzone.thedonorzone.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var username: String,
    val email: String,
    var password: String
) {
    // No-argument constructor required by Hibernate
    constructor() : this(null, "", "", "")
}