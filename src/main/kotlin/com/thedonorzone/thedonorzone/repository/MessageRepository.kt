package com.thedonorzone.thedonorzone.repository

import com.thedonorzone.thedonorzone.model.Conversation
import com.thedonorzone.thedonorzone.model.Message
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository : JpaRepository<Message, Long> {
    fun findByConversation(conversation: Conversation): List<Message>
}
