package web.planorama.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import web.planorama.demo.dto.EstudanteDTO;
import web.planorama.demo.service.AdministradorService;
import web.planorama.demo.service.EstudanteService;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final AdministradorService admService;
    private final EstudanteService estudanteService;
}
