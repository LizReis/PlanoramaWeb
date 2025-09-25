package web.planorama.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import web.planorama.demo.dto.DesempenhoDTO;
import web.planorama.demo.entity.UsuarioEntity;
import web.planorama.demo.exceptions.MyNotFoundException;
import web.planorama.demo.repository.UsuarioRepository;
import web.planorama.demo.service.RegistrarEstudoService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PainelDesempenhoController {

    private final RegistrarEstudoService registrarEstudoService;
    private final UsuarioRepository usuarioRepository;

    @GetMapping("/painelDesempenho")
    public String painelDesempenho() {
        return "painelDesempenho";
    }

    @GetMapping("/api/desempenho")
    @ResponseBody
    public ResponseEntity<List<DesempenhoDTO>> getDesempenhoData(@AuthenticationPrincipal UserDetails userDetails) {
        UsuarioEntity usuario = usuarioRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new MyNotFoundException("Usuário não encontrado."));

        List<DesempenhoDTO> desempenho = registrarEstudoService.getDesempenhoPorMateria(usuario.getId());

        log.info("Retornando dados de desempenho para o usuário {}: {}", usuario.getEmail(), desempenho);
        return ResponseEntity.ok(desempenho);
    }

}
