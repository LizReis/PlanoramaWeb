package web.planorama.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import web.planorama.demo.service.EstudanteService;

@Controller
@RequestMapping("/minha-conta")
@RequiredArgsConstructor
public class AlterarDadosEstudanteController {
    private final EstudanteService estudanteService;

    private final String UPLOAD_DIR = "uploadsUser";

    @PostMapping("/alterar-foto-perfil")
    public String alterarFotoPerfil(@RequestParam("novaFoto") MultipartFile novaFoto,
            RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails) {
        if (novaFoto.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Por favor, selecione uma nova foto antes de salvar");
            return "redirect:/minha-conta";
        }

        try{
            String fileName = UUID.randomUUID().toString() + "_" + novaFoto.getOriginalFilename();
            Path caminhoDoArquivo = Paths.get(UPLOAD_DIR, fileName);
            Files.createDirectories(caminhoDoArquivo.getParent());
            Files.copy(novaFoto.getInputStream(), caminhoDoArquivo, StandardCopyOption.REPLACE_EXISTING);

            String UrlFile = "/uploadsUser/" + fileName;

            String emailUsuarioLogado = userDetails.getUsername();

            estudanteService.alterarFotoUsuario(emailUsuarioLogado, UrlFile);

            redirectAttributes.addFlashAttribute("success", "Foto alterada com sucesso!");
        }catch(IOException e){
            redirectAttributes.addFlashAttribute("error", "Falha ao fazer upload da nova foto.");
        }catch(RuntimeException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/minha-conta";
    }

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
    public String alterarEmail(@RequestParam("novoEmail") String novoEmail,
            @RequestParam("senhaAtual") String senhaAtual, RedirectAttributes redirectAttributes,
            HttpServletRequest request, HttpServletResponse response) {
        if (senhaAtual.isBlank() || senhaAtual.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "O campo de senha atual não pode estar vazio.");
            return "redirect:/minha-conta?error";
        } else if (novoEmail.isBlank() || novoEmail.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "O campo do novo e-mail não pode estar vazio");
            return "redirect:/minha-conta?error";
        } else {
            try {
                estudanteService.alterarEmail(novoEmail, senhaAtual);
                Authentication autenticacaoAtual = SecurityContextHolder.getContext().getAuthentication();
                if (autenticacaoAtual != null) {
                    // Responsável por fazer o logout do usuário
                    new SecurityContextLogoutHandler().logout(request, response, autenticacaoAtual);

                    redirectAttributes.addFlashAttribute("success",
                            "E-mail alterado com sucesso! Faça o login novamente.");
                    return "redirect:/login";
                }
            } catch (RuntimeException e) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
            }
            return "redirect:/minha-conta";
        }
    }

    @PostMapping("/alterar-nome-usuario")
    public String alterarNomeUsuario(@RequestParam("novoNome") String novoNome,
            @RequestParam("senhaAtual") String senhaAtual, RedirectAttributes redirectAttributes) {
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
    public String alterarDescricao(@RequestParam("novaDescricao") String novaDescricao,
            @RequestParam("senhaAtual") String senhaAtual, RedirectAttributes redirectAttributes) {
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
