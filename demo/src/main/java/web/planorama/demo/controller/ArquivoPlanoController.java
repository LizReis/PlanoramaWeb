package web.planorama.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import web.planorama.demo.repository.PlanejamentoRepository;
import web.planorama.demo.service.PlanejamentoService;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/arquivo")
@RequiredArgsConstructor
@Slf4j
public class ArquivoPlanoController {

    private final PlanejamentoService planejamentoService;

    @GetMapping()
    public String getMethodName(Model model) {
        List<Object> allPlanejamentosArquivados = new ArrayList<>();

        allPlanejamentosArquivados.addAll(planejamentoService.findAllPlanejamentoIsArquivado());
        
        model.addAttribute("allPlanejamentosArquivados", allPlanejamentosArquivados);

        return "arquivoPlano";
    }
    
}
