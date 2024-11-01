package com.thedonorzone.thedonorzone.controller

import UserDto
import com.thedonorzone.thedonorzone.model.User
import com.thedonorzone.thedonorzone.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @PostMapping("/create")
    fun createAccount(@RequestBody userDto: UserDto): ResponseEntity<User> {
        return try {
            val user = userService.createUser(userDto)
            ResponseEntity(user, HttpStatus.CREATED)
        } catch (e: RuntimeException) {
            ResponseEntity(HttpStatus.CONFLICT)
        }
    }
}