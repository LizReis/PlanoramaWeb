package web.planorama.demo.controller;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import web.planorama.demo.dto.EstudanteDTO;
import web.planorama.demo.dto.UsuarioDTO;
import web.planorama.demo.service.EstudanteService;
import web.planorama.demo.service.UsuarioService;

import org.springframework.web.bind.annotation.GetMapping;


import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/minha-conta")
@RequiredArgsConstructor
public class MinhaContaController {
    private final EstudanteService estudanteService;

    @GetMapping
    public String minhaConta(Authentication authentication, Model model) {
        String email = authentication.getName();

        EstudanteDTO usuarioLogado = estudanteService.findByEmail(email);

        model.addAttribute("usuarioLogado", usuarioLogado);

        return "minhaConta";
    }

    @GetMapping("/alterar-foto-perfil")
    public String getAlterarFotoFragment(){
        return "alterarFotoPerfil :: cardAlteracao";
    }

    @GetMapping("/alterar-nome-usuario")
    public String getAlterarNomeFragment(){
        return "alterarNomeEstudante :: cardAlteracao";
    }

    @GetMapping("/alterar-email")
    public String getAlterarEmailFragment(){
        return "alterarEmailEstudante :: cardAlteracao";
    }

    @GetMapping("/alterar-senha")
    public String getAlterarSenhaFragment(){
        return "alterarSenhaEstudante :: cardAlteracao";
    }

    @GetMapping("/alterar-descricao")
    public String getAlterarDescricaoFragment(){
        return "alterarDescricaoUsuario :: cardAlteracao";
    }
}
