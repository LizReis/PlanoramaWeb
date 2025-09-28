package web.planorama.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String login(@RequestParam(defaultValue = "false", required = false)
    Boolean cadastroSucesso, Model model) {

        model.addAttribute("cadastroSucesso", cadastroSucesso);
        return "login";
    }

    @GetMapping("/cadastro")
    public String getTelaCadastro() {
        return "cadastro";
    }
    
    
}
