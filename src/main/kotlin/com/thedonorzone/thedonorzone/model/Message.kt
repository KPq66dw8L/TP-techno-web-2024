package com.thedonorzone.thedonorzone.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "messages")
data class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "conversation_id", nullable = false)
    val conversation: Conversation,

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    val sender: User,

    @Column(nullable = false)
    val content: String,

    @Column(nullable = false)
    val timestamp: LocalDateTime = LocalDateTime.now()
)
