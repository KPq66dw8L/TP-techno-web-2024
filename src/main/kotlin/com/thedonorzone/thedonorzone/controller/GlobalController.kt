package com.thedonorzone.thedonorzone.controller

import jakarta.servlet.http.HttpSession
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

//@RestController
//@RequestMapping("/api/auth")
//class AuthController {
//    @PostMapping("/login")
//    fun login(@RequestBody request: Map<String, String>): ResponseEntity<Map<String, String>> {
//        val username = request["username"] ?: return ResponseEntity.badRequest().build()
//
//        // Optionally, validate the username and password here
//        val token = JwtTokenUtil.generateToken(username)
//        return ResponseEntity.ok(mapOf("token" to token))
//    }
//}

@Controller
class GlobalController {
    @GetMapping("/")
    fun home(model: Model, session: HttpSession): String {
        val jwtToken = session.getAttribute("jwtToken")
        return if (jwtToken == null) {
            "redirect:/login"
        } else {
            "index"
        }
    }
}