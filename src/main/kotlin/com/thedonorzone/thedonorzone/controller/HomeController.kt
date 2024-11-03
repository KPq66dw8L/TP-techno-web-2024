package com.thedonorzone.thedonorzone.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Controller
class HomeController {
    @RequestMapping("/")
    fun home(model: Model): String {
        model.addAttribute("message", "Welcome to My Web App!")
        return "index"
    }
}