package web.planorama.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import web.planorama.demo.dto.PlanejamentoDTO;
import web.planorama.demo.entity.UsuarioEntity;
import web.planorama.demo.mapping.PlanejamentoMapper;
import web.planorama.demo.repository.PlanejamentoRepository;
import web.planorama.demo.repository.UsuarioRepository;


@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UsuarioRepository usuarioRepository;
    private final PlanejamentoRepository planejamentoRepository;
    private final PlanejamentoMapper planejamentoMapper;

    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails) {

        Optional<UsuarioEntity> usuarioOptional = usuarioRepository.findByEmail(userDetails.getUsername());

        if(usuarioOptional.isPresent()){
            UsuarioEntity usuarioLogado = usuarioOptional.get();

            List<PlanejamentoDTO> planejamentosUsuario = planejamentoRepository.findAllByCriador(usuarioLogado).stream().map(planejamento -> {
                PlanejamentoDTO planejamentoDTO = planejamentoMapper.toPlanejamentoDTO(planejamento);

                return planejamentoDTO;
            }).collect(Collectors.toList());

            model.addAttribute("planejamentosUsuarioLogado", planejamentosUsuario);
        }

        return "home";
    }

    @GetMapping("/admin/homeAdm")
    public String homeAdm() {
        return "homeAdm";
    }
}
