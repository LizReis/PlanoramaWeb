package web.planorama.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import web.planorama.demo.service.PlanejamentoService;

//CONTROLLER PARA LISTAR OS PLANEJAMENTOS CRIADOS PELO ADMIN
@Controller
@RequestMapping("/admin/listar-planejamentos")
@RequiredArgsConstructor
@Slf4j
public class AdministrarPlanejamentosController {

    private final PlanejamentoService planejamentoService;

    @GetMapping
    public String listAll(Model model) {
        List<Object> allPlanejamentos = new ArrayList<>();

        allPlanejamentos.addAll(planejamentoService.findAllOfAdm());
        
        model.addAttribute("allPlanejamentos", allPlanejamentos);
        return "administrarPlanosAdm";
    }
}
