package com.thedonorzone.thedonorzone.repository

import com.thedonorzone.thedonorzone.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun findById(id: UUID): User?
    fun findByUsername(username: String?): User?
}