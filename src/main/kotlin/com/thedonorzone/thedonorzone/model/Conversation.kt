package com.thedonorzone.thedonorzone.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "conversations")
data class Conversation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user1_id", nullable = false)
    val user1: User?,

    @ManyToOne
    @JoinColumn(name = "user2_id", nullable = false)
    val user2: User?,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
){
    // No-argument constructor required by Hibernate
    constructor() : this(null, null, null, LocalDateTime.now())
}
