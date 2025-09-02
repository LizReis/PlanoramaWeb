package web.planorama.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.planorama.demo.entity.UsuarioEntity;

@Controller
@RequestMapping("/planos")
public class PlanejamentoController {

    @GetMapping("/novo")
    public String exibirFormularioNovoPlano(Model model, @AuthenticationPrincipal UsuarioEntity usuarioLogado) {
        // O @AuthenticationPrincipal injeta o objeto do usuário logado.

        model.addAttribute("usuarioId", usuarioLogado.getId());
        return "criarPlano";
    }
}