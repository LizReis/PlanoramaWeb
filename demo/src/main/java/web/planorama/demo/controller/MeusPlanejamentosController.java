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
import web.planorama.demo.entity.UsuarioEntity;
import web.planorama.demo.exceptions.MyNotFoundException;
import web.planorama.demo.mapping.UsuarioMapper;
import web.planorama.demo.repository.UsuarioRepository;
import web.planorama.demo.service.PlanejamentoService;
import web.planorama.demo.service.UsuarioService;

@Controller
@RequestMapping("/meus-planejamentos")
@RequiredArgsConstructor
@Slf4j
public class MeusPlanejamentosController {
    
    private final PlanejamentoService planejamentoService;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @GetMapping
    public String getAllPlanejamentos(Model model) {
        List<Object> allPlanejamentosUsuario = new ArrayList<>();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String emailUsuarioLogado = ((UserDetails) principal).getUsername();

        UsuarioDTO criador = usuarioMapper.toUsuarioDTO(usuarioRepository.findByEmail(emailUsuarioLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado para finalização do plano."))); 

        allPlanejamentosUsuario.addAll(planejamentoService.findAllOfEstudante(criador));
        
        model.addAttribute("allPlanejamentos", allPlanejamentosUsuario);
        return "meusPlanos";
    }
    
}
