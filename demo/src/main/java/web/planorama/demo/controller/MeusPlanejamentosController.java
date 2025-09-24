package web.planorama.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import web.planorama.demo.dto.PlanejamentoProgressDTO;
import web.planorama.demo.entity.UsuarioEntity;
import web.planorama.demo.repository.UsuarioRepository;
import web.planorama.demo.service.PlanejamentoService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MeusPlanejamentosController {

    private final PlanejamentoService planejamentoService;
    private final UsuarioRepository usuarioRepository;

    @GetMapping("/meus-planejamentos")
    public String meusPlanos(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        
        Optional<UsuarioEntity> usuarioOptional = usuarioRepository.findByEmail(userDetails.getUsername());

        if(usuarioOptional.isPresent()) {
            UsuarioEntity usuarioLogado = usuarioOptional.get();
            List<PlanejamentoProgressDTO> planejamentosComProgresso = planejamentoService.findAllComProgressoByUsuario(usuarioLogado);

            model.addAttribute("planejamentosUsuarioLogado", planejamentosComProgresso);
        }

        return "meusPlanos";
    }
}