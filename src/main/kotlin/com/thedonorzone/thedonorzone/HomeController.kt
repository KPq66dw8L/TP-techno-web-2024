package com.thedonorzone.thedonorzone

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/")
class HomeController {
    @RequestMapping
    @ResponseBody
    fun rootGreeting(): String = "Hello World!"
}