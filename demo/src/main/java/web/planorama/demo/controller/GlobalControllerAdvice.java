package web.planorama.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import web.planorama.demo.dto.UsuarioDTO;
import web.planorama.demo.service.UsuarioService;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {
    private final UsuarioService usuarioService;

    @ModelAttribute("usuarioLogado")
    public UsuarioDTO getUsuarioLogado(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            return usuarioService.findByEmail(userDetails.getUsername());
        }
        return null; // Significa que não tem usuário logado
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    public ModelAndView handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ModelAndView("paginaErro");
    }
}
