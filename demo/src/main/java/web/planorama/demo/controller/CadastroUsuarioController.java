package web.planorama.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import web.planorama.demo.dto.AdministradorDTO;
import web.planorama.demo.dto.EstudanteDTO;
import web.planorama.demo.service.AdministradorService;
import web.planorama.demo.service.EstudanteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/cadastro")
@RequiredArgsConstructor
@Slf4j
public class CadastroUsuarioController {

    private final AdministradorService admService;
    private final EstudanteService estudanteService;

    @GetMapping
    public String pagina(){
        return "cadastro";
    }

    @PostMapping("/estudante")
    public String create(EstudanteDTO estudanteDTO) {
        log.info("Request: {}", estudanteDTO);
        estudanteService.save(estudanteDTO);
        return "login";
    }
    

    @PostMapping("/adms")
    public String create(AdministradorDTO administradorDTO){
        log.info("Request: {}", administradorDTO);
        admService.save(administradorDTO);
        return "login";
    }

}
