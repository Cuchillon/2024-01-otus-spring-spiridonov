package com.ferick.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MainPageController {

    @GetMapping("/")
    fun mainPage(model: Model): String {
        return "index"
    }
}
