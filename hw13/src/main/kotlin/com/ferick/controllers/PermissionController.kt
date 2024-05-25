package com.ferick.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PermissionController {

    @GetMapping("/access-denied")
    fun accessDenied(): String {
        return "forbidden"
    }
}
