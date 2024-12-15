package com.thedonorzone.thedonorzone.controller

import com.thedonorzone.thedonorzone.model.User
import com.thedonorzone.thedonorzone.repository.UserRepository
import com.thedonorzone.thedonorzone.service.ChatService
import com.thedonorzone.thedonorzone.service.UserService
import jakarta.servlet.http.HttpSession
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@Controller
@RequestMapping("/chat")
class ChatController(
    private val userService: UserService,
    private val chatService: ChatService
) {

    // Show all conversations for the logged-in user
    @GetMapping
    fun getConversations(session: HttpSession, model: Model): String {
        val currentUser = session.getAttribute("currentUser") as User?
            ?: return "redirect:/login"

        val conversations = chatService.getConversationsForUser(currentUser)
        model.addAttribute("conversations", conversations)
        return "conversations"
    }

    @PostMapping("/new")
    fun createConversation(
        @RequestParam("username") username: String,
        session: HttpSession
    ): String {
        val currentUser = session.getAttribute("currentUser") as User?
            ?: return "redirect:/login"

        val targetUser = userService.findByUsername(username)
            ?: return "redirect:/conversation?error=User+not+found"

        if (currentUser.id == targetUser.id) {
            return "redirect:/conversation?error=Cannot+chat+with+yourself"
        }

        val conversation = chatService.createOrGetConversation(currentUser, targetUser)
        return "redirect:/conversation/${conversation.id}"
    }


    // Get messages for a specific conversation
    @GetMapping("/{id}")
    fun getConversation(@PathVariable id: Long, session: HttpSession, model: Model): String {
        val currentUser = session.getAttribute("currentUser") as User?
            ?: return "redirect:/login"

        val conversation = chatService.getConversationById(id)
            ?: return "redirect:/chat?error=Conversation+not+found"

        // Ensure the current user is part of the conversation
        if (conversation.user1?.id != currentUser.id && conversation.user2?.id != currentUser.id) {
            return "redirect:/chat?error=Access+denied"
        }

        val messages = chatService.getMessages(conversation)
        model.addAttribute("conversation", conversation)
        model.addAttribute("messages", messages)
        return "conversation"
    }

    @PostMapping("/{id}/send")
    fun sendMessage(
        @PathVariable("id") conversationId: Long,
        @RequestParam("content") content: String,
        model: Model,
        session: HttpSession
    ): String {
        val conversation = chatService.getConversationById(conversationId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Conversation not found")

        val sender = session.getAttribute("currentUser") as User?
            ?: return "redirect:/login"

        chatService.sendMessage(conversation, sender, content)

        return "redirect:/chat/$conversationId"
    }

}
