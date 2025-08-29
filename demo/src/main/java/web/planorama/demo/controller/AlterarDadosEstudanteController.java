package web.planorama.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import lombok.RequiredArgsConstructor;
import web.planorama.demo.service.EstudanteService;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/minha-conta")
@RequiredArgsConstructor
public class AlterarDadosEstudanteController {
    private final EstudanteService estudanteService;

    @PostMapping("/alterar-senha")
    public String alterarSenha(@RequestParam("senhaAtual") String senhaAtual,
            @RequestParam("novaSenha") String novaSenha, RedirectAttributes redirectAttributes) {
        if (senhaAtual.isBlank() || senhaAtual.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "O campo de senha atual não pode estar vazio.");
            return "redirect:/minha-conta?error";
        } else if (novaSenha.isBlank() || novaSenha.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "O campo de nova senha não pode estar vazio.");
            return "redirect:/minha-conta?error";
        } else {
            try {
                estudanteService.alterarSenha(senhaAtual, novaSenha);
                redirectAttributes.addFlashAttribute("success", "Senha alterada com sucesso!");
            } catch (RuntimeException e) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
            }
            return "redirect:/minha-conta";
        }
    }

    @PostMapping("/alterar-email")
    public String alterarEmail(@RequestParam("novoEmail") String novoEmail, @RequestParam("senhaAtual") String senhaAtual, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
        if(senhaAtual.isBlank() || senhaAtual.isEmpty()){
            redirectAttributes.addFlashAttribute("error", "O campo de senha atual não pode estar vazio.");
            return "redirect:/minha-conta?error";
        }else if(novoEmail.isBlank() || novoEmail.isEmpty()){
            redirectAttributes.addFlashAttribute("error", "O campo do novo e-mail não pode estar vazio");
            return "redirect:/minha-conta?error";
        }else{
            try {
                estudanteService.alterarEmail(novoEmail, senhaAtual);
                Authentication autenticacaoAtual = SecurityContextHolder.getContext().getAuthentication();
                if (autenticacaoAtual != null) {
                    //Responsável por fazer o logout do usuário
                    new SecurityContextLogoutHandler().logout(request, response, autenticacaoAtual);

                    redirectAttributes.addFlashAttribute("success", "E-mail alterado com sucesso! Faça o login novamente.");
                    return "redirect:/login";
                }
            } catch (RuntimeException e) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
            }
            return "redirect:/minha-conta";
        }
    }
    
    @PostMapping("/alterar-nome-usuario")
    public String alterarNomeUsuario(@RequestParam("novoNome") String novoNome, @RequestParam("senhaAtual") String senhaAtual, RedirectAttributes redirectAttributes) {
        if (senhaAtual.isBlank() || senhaAtual.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "O campo de senha atual não pode estar vazio.");
            return "redirect:/minha-conta?error";
        } else if (novoNome.isBlank() || novoNome.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "O campo do novo nome não pode estar vazio.");
            return "redirect:/minha-conta?error";
        } else {
            try {
                estudanteService.alterarNomeUsuario(novoNome, senhaAtual);
                redirectAttributes.addFlashAttribute("success", "Nome alterado com sucesso!");
            } catch (RuntimeException e) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
            }
            return "redirect:/minha-conta";
        }

    }

    @PostMapping("/alterar-descricao")
    public String alterarDescricao(@RequestParam("novaDescricao") String novaDescricao, @RequestParam("senhaAtual") String senhaAtual, RedirectAttributes redirectAttributes) {
        if (senhaAtual.isBlank() || senhaAtual.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "O campo de senha atual não pode estar vazio.");
            return "redirect:/minha-conta?error";
        } else if (novaDescricao.isBlank() || novaDescricao.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "O campo da descrição não pode estar vazio.");
            return "redirect:/minha-conta?error";
        } else {
            try {
                estudanteService.alterarDescricao(novaDescricao, senhaAtual);
                redirectAttributes.addFlashAttribute("success", "Descrição alterada com sucesso!");
            } catch (RuntimeException e) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
            }
            return "redirect:/minha-conta";
        }
    }
    
}
