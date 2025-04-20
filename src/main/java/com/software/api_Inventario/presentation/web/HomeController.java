package com.software.api_Inventario.presentation.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class HomeController {

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @GetMapping("/home")
    public String mostrarHome() {
        return "home";
    }

    @GetMapping("/registro")
    public String mostrarFormRegistro() {
        return "registroUsuario";
    }
}
