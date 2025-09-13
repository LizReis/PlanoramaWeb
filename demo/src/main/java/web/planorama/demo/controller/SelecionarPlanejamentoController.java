package web.planorama.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import web.planorama.demo.repository.PlanejamentoRepository;
import web.planorama.demo.service.PlanejamentoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/selecionar-planejamento")
@RequiredArgsConstructor
@Slf4j
public class SelecionarPlanejamentoController {
    private final PlanejamentoService planejamentoService;

    @GetMapping
    public String listAll(Model model) {
        List<Object> allPlanejamentos = new ArrayList<>();

        allPlanejamentos.addAll(planejamentoService.findAllOfAdm());
        
        model.addAttribute("allPlanejamentos", allPlanejamentos);
        return "selecionarPlano";
    }

    @PostMapping
    public String selecionarPlanejamento(@RequestParam("id") UUID idPlanejamento, RedirectAttributes redirectAttributes) {
        try{
            planejamentoService.selecionarPlanoPredefinido(idPlanejamento);

            redirectAttributes.addFlashAttribute("success", "Planejamento selecionado com sucesso! Olhe na aba 'Meus Planejamentos ou na Home'");
        }catch(RuntimeException e){
            redirectAttributes.addFlashAttribute("error", "Ocorreu um erro ao selecionar o Planejamento.");
        }

        return "redirect:/selecionar-planejamento";
    }
    
}
