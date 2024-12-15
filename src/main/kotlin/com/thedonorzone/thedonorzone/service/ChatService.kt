package com.thedonorzone.thedonorzone.service

import com.thedonorzone.thedonorzone.model.Conversation
import com.thedonorzone.thedonorzone.model.Message
import com.thedonorzone.thedonorzone.model.User
import com.thedonorzone.thedonorzone.repository.ConversationRepository
import com.thedonorzone.thedonorzone.repository.MessageRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ChatService(
    private val conversationRepository: ConversationRepository,
    private val messageRepository: MessageRepository
) {
    fun getOrCreateConversation(user1: User, user2: User): Conversation {
        return conversationRepository.findByUser1AndUser2(user1, user2)
            ?: conversationRepository.save(Conversation(user1 = user1, user2 = user2))
    }

    fun sendMessage(conversation: Conversation, sender: User, content: String) {
        val message = Message(
            conversation = conversation,
            sender = sender,
            content = content,
            timestamp = LocalDateTime.now()
        )
        messageRepository.save(message)
    }


    fun getMessages(conversation: Conversation): List<Message> {
        return messageRepository.findByConversation(conversation)
    }

    fun getConversationById(conversationId: Long): Conversation? {
        return conversationRepository.findById(conversationId).orElse(null)
    }

    fun getConversationsForUser(user: User): List<Conversation> {
        return conversationRepository.findByUser1OrUser2(user, user)
    }

    fun createOrGetConversation(user1: User, user2: User): Conversation {
        // Check if the conversation already exists
        val existingConversation = conversationRepository.findByUsers(user1, user2)
        if (existingConversation != null) {
            return existingConversation
        }

        // Create a new conversation
        val newConversation = Conversation(
            user1 = user1,
            user2 = user2
        )
        return conversationRepository.save(newConversation)
    }

}
