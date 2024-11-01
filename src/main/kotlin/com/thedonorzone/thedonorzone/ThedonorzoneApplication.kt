package com.thedonorzone.thedonorzone

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = arrayOf("com.thedonorzone.thedonorzone"))
class ThedonorzoneApplication

fun main(args: Array<String>) {
	runApplication<ThedonorzoneApplication>(*args)
}
