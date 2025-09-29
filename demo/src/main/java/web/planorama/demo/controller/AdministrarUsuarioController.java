package web.planorama.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import web.planorama.demo.dto.UsuarioDTO;
import web.planorama.demo.exceptions.MyNotFoundException;
import web.planorama.demo.service.UsuarioService;

@Controller
@RequestMapping("/admin/listar-usuarios")
@RequiredArgsConstructor
@Slf4j
public class AdministrarUsuarioController {

    private final UsuarioService usuarioService;

    //LISTA OS USUÁRIOS NA TELA DE ADM USUÁRIOS
    @GetMapping
    public String listAll(Model model, @RequestParam(name = "tipo", defaultValue = "todos") String tipoUsuario) {
        List<Object> allUsers = new ArrayList<>();

        if ("estudante".equals(tipoUsuario)) {
            allUsers.addAll(usuarioService.findAllEstudantes());
        } else if ("admin".equals(tipoUsuario)) {
            allUsers.addAll(usuarioService.findAllAdmins());
        } else {
            allUsers.addAll(usuarioService.findAll());
        }

        model.addAttribute("listaDeUsuarios", allUsers);
        // Esse model.addAttribute ajuda a corrigir um erro da view
        // matem a opção correta selecionada após recarregar a página (tipo o negócio de
        // voltar
        // o que o usuario ja digitou no form)
        model.addAttribute("tipoSelecionado", tipoUsuario);
        return "administrarUsuario";
    }

    //CHAMA O CARD PARA ALTERAR USUÁRIO
    @GetMapping("/alterar-usuario/{id}")
    public String getAlterarDadosUsuarioFragment(@PathVariable("id") UUID usuarioId, Model model) {
        try {
            UsuarioDTO usuarioParaAlterar = usuarioService.findOne(usuarioId);
            model.addAttribute("usuario", usuarioParaAlterar);
        } catch (MyNotFoundException e) {
            model.addAttribute("erroNotFound", e.getMessage());
        }

        return "alterarUsuarioADM :: cardAlteracao";
    }

    //POST PARA FAZER A ALTERAÇÃO DAS INFORMAÇÕES DO USUÁRIO
    @PostMapping("/alterar-usuario")
    public String postAlterarDadosUsuario(@RequestParam("id") UUID usuarioParaAlterar,
            @RequestParam("novoNome") String novoNomeUsuario,
            @RequestParam("novoEmail") String novoEmailUsuario, @RequestParam("senhaAtual") String senhaADM,
            RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {

        if (novoNomeUsuario.isBlank() || novoNomeUsuario.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "O campo de Novo Nome do Usuário não pode estar vazio.");
            return "redirect:/admin/listar-usuarios?error";
        } else if (novoEmailUsuario.isBlank() || novoEmailUsuario.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "O campo de Novo E-mail do Usuário não pode estar vazio.");
            return "redirect:/admin/listar-usuarios?error";
        } else if (senhaADM.isBlank() || senhaADM.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Você deve inserir a sua Senha de ADM.");
            return "redirect:/admin/listar-usuarios?error";
        } else if (novoNomeUsuario.isEmpty() && novoEmailUsuario.isEmpty() && senhaADM.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Todos os campos devem ser preenchidos.");
            return "redirect:/admin/listar-usuarios?error";
        } else {
            try {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String emailAdmLogado = authentication.getName();
                UsuarioDTO adminLogado = usuarioService.findByEmail(emailAdmLogado);

                usuarioService.alterarDadoUsuario(usuarioParaAlterar, novoNomeUsuario, novoEmailUsuario, senhaADM);
                redirectAttributes.addFlashAttribute("success", "Usuário alterado com sucesso!");

                if (adminLogado.id().equals(usuarioParaAlterar)) {
                    //O ADM ALTEROU O PRÓPRIO EMAIL ENT PRECISA REFAZER O LOGIN NO SISTEMA
                    new SecurityContextLogoutHandler().logout(request, response, authentication);
                    redirectAttributes.addFlashAttribute("success",
                            "Seus dados foram alterados com sucesso! Por favor, faça o login novamente.");
                    return "redirect:/login";
                }else{
                    redirectAttributes.addFlashAttribute("success",
                            "Usuário alterado com Sucesso!");
                }
            } catch (RuntimeException e) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
            }
            return "redirect:/admin/listar-usuarios";
        }
    }

    //GET PARA REMOÇÃO DE USUÁRIO POR ID
    @GetMapping("/remover-usuario/{id}")
    public String getRemoverUsuario(@PathVariable("id") UUID usuarioParaRemover, RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal UserDetails userDetails) {// AuthenticationPrincipal UserDetails é quem pega o
                                                               // usuário logado

        UsuarioDTO adminLogado = usuarioService.findByEmail(userDetails.getUsername());

        if (adminLogado != null && adminLogado.id().equals(usuarioParaRemover)) {
            redirectAttributes.addFlashAttribute("error",
                    "Você não pode remover sua conta de administrador do sistema.");
            return "redirect:/listar-usuarios";
        }

        try {
            usuarioService.remove(usuarioParaRemover);
            redirectAttributes.addFlashAttribute("success", "Usuário removido com sucesso!");
        } catch (MyNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/listar-usuarios";
    }
}
