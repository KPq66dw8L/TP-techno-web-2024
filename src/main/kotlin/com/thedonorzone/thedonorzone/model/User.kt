package com.thedonorzone.thedonorzone.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var username: String? = null,  // Allow username to be nullable
    val email: String? = null,    // Allow email to be nullable
    var password: String          // Keep password non-nullable as it's required
) {
    // No-argument constructor required by Hibernate
    constructor() : this(null, null, null, "")
}
