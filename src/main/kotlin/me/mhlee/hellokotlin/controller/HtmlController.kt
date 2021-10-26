package me.mhlee.hellokotlin.controller

import me.mhlee.hellokotlin.domain.User
import me.mhlee.hellokotlin.domain.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.lang.RuntimeException
import java.security.MessageDigest
import javax.servlet.http.HttpSession

@Controller
class HtmlController {

    @Autowired
    lateinit var repository: UserRepository

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

    @PostMapping("/sign")
    fun postSign(model: Model,
                 @RequestParam(value = "id") userId: String,
                 @RequestParam(value = "password") password: String): String {
        try {
            val user = repository.save(User(userId, sh256(password)))
            println(user.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        model.addAttribute("title", "sign success")
        return "login"
    }

    fun sh256(str: String): String {
        val sha = MessageDigest.getInstance("SHA-256")
        val hex = sha.digest(str.toByteArray())
        return hex.fold("") { str, it -> str + "%02x".format(it) }
    }

    @PostMapping("/login")
    fun login(model: Model,
              session: HttpSession,
              @RequestParam(value = "id") userId: String,
              @RequestParam(value = "password") password: String): String {
        model.addAttribute("title", "로그인 실패")
        var next = "login"
        try {
            val hash = sh256(password)
            val user = repository.findByUserId(userId)

            if (user?.password == hash) {
                session.setAttribute("userId", userId)
                model.addAttribute("title", "welcome")
                model.addAttribute("userId", userId)
                next = "welcome"
            }
        } catch(e: Exception) {
            e.printStackTrace()
        }

        return next
    }
}