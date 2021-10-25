package me.mhlee.hellokotlin.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.lang.RuntimeException

@Controller
class HtmlController {

    @GetMapping("/")
    fun index(model: Model): String {
        return "index"
    }

    @GetMapping("/{formType}")
    fun sign(model: Model, @PathVariable formType: String): String {
        var response : String = ""

        if (formType == "sign") response = "sign"
        else if (formType == "login") response = "login"
        else throw RuntimeException("알수없는 타입")

        model.addAttribute("title", response)

        return response
    }

    @GetMapping("/login")
    fun login(model: Model): String {
        return "login"
    }

}