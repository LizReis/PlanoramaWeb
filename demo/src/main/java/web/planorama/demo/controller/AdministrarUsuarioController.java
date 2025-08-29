package web.planorama.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import web.planorama.demo.dto.UsuarioDTO;
import web.planorama.demo.service.AdministradorService;
import web.planorama.demo.service.EstudanteService;
import web.planorama.demo.service.UsuarioService;

@Controller
@RequestMapping("/listar-usuarios")
@RequiredArgsConstructor
@Slf4j
public class AdministrarUsuarioController {

    private final AdministradorService admService;
    private final EstudanteService estudanteService;
    private final UsuarioService usuarioService;

    @GetMapping
    public String listAll(Model model, @RequestParam(name = "tipo", defaultValue = "todos") String tipoUsuario) {
        List<Object> allUsers = new ArrayList<>();

        if ("estudante".equals(tipoUsuario)) {
            allUsers.addAll(estudanteService.findAll());
        } else if ("admin".equals(tipoUsuario)) {
            allUsers.addAll(admService.findAll());
        } else {
            allUsers.addAll(estudanteService.findAll());
            allUsers.addAll(admService.findAll());
        }

        model.addAttribute("listaDeUsuarios", allUsers);
        //Esse model.addAttribute ajuda a corrigir um erro da view
        //matem a opção correta selecionada após recarregar a página (tipo o negócio de voltar 
        //o que o usuario ja digitou no form)
        model.addAttribute("tipoSelecionado", tipoUsuario); 
        return "administrarUsuario";
    }

    @GetMapping("/alterar-usuario")
    public String getAlterarDadosUsuarioFragment(@RequestParam("id") UUID usuarioId, Model model){
        UsuarioDTO usuarioParaAlterar = usuarioService.findOne(usuarioId);

        model.addAttribute("usuario", usuarioParaAlterar);

        return "alterarUsuarioADM :: cardAlteracao";
    }

    @PostMapping("/alterar-usuario")
    public String postAlterarDadosUsuario(@RequestParam("id") UUID usuarioParaAlterar, @RequestParam("novoNome") String novoNomeUsuario,
            @RequestParam("novoEmail") String novoEmailUsuario, @RequestParam("senhaAtual") String senhaADM,
            RedirectAttributes redirectAttributes) {

        if (novoNomeUsuario.isBlank() || novoNomeUsuario.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "O campo de Novo Nome do Usuário não pode estar vazio.");
            return "redirect:/listar-usuarios?error";
        } else if (novoEmailUsuario.isBlank() || novoEmailUsuario.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "O campo de Novo E-mail do Usuário não pode estar vazio.");
            return "redirect:/listar-usuarios?error";
        } else if (senhaADM.isBlank() || senhaADM.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Você deve inserir a sua Senha de ADM.");
            return "redirect:/listar-usuarios?error";
        }else if(novoNomeUsuario.isEmpty() && novoEmailUsuario.isEmpty() && senhaADM.isEmpty()){
            redirectAttributes.addFlashAttribute("error", "Todos os campos devem ser preenchidos.");
            return "redirect:/listar-usuarios?error";
        }else{
            try{
                admService.alterarDadoUsuario(usuarioParaAlterar, novoNomeUsuario, novoEmailUsuario, senhaADM);
                redirectAttributes.addFlashAttribute("success", "Usuário alterado com sucesso!");
            }catch(RuntimeException e){
                redirectAttributes.addFlashAttribute("error", e.getMessage());
            }
            return "redirect:/listar-usuarios";
        }
    }
}
