package me.mhlee.hellokotlin.controller

import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HtlController {

    @GetMapping("/in")
    fun index(model: Model): String {
        return "index"
    }
}