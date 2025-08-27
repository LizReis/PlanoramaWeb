package web.planorama.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import web.planorama.demo.service.UsuarioService;

import org.springframework.web.bind.annotation.GetMapping;


import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/minha-conta")
@RequiredArgsConstructor
public class MinhaContaController {
    private final UsuarioService usuarioService;

    @GetMapping
    public String minhaConta() {
        return "minhaConta";
    }
    

}
