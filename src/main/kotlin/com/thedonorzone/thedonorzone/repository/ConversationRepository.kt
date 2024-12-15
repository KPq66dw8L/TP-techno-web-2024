package com.thedonorzone.thedonorzone.repository

import com.thedonorzone.thedonorzone.model.Conversation
import com.thedonorzone.thedonorzone.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ConversationRepository : JpaRepository<Conversation, Long> {
    fun findByUser1AndUser2(user1: User, user2: User): Conversation?
    fun findByUser1OrUser2(user: User, userAlt: User): List<Conversation>
    @Query("""
        SELECT c FROM Conversation c 
        WHERE (c.user1 = :user1 AND c.user2 = :user2) 
           OR (c.user1 = :user2 AND c.user2 = :user1)
    """)
    fun findByUsers(@Param("user1") user1: User, @Param("user2") user2: User): Conversation?
}
