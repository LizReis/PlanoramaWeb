package web.planorama.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import web.planorama.demo.service.AdministradorService;
import web.planorama.demo.service.EstudanteService;

@Controller
@RequestMapping("/listar-usuarios")
@RequiredArgsConstructor
@Slf4j
public class AdministrarUsuarioController {

    private final AdministradorService admService;
    private final EstudanteService estudanteService;


    @GetMapping
    public String listAll(Model model){
        List<Object> allUsers = new ArrayList<>();

        allUsers.addAll(estudanteService.findAll());
        allUsers.addAll(admService.findAll());

        model.addAttribute("listaDeUsuarios", allUsers);
        return "administrarUsuario";
    }
}
