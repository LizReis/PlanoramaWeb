package web.planorama.demo.controller;

// ... imports
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import web.planorama.demo.dto.PlanejamentoDTO;
import web.planorama.demo.dto.SessaoEstudoDTO;
import web.planorama.demo.service.PlanejamentoService;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/planejamento")
public class PlanejamentoController {

    private final PlanejamentoService planejamentoService;

    public PlanejamentoController(PlanejamentoService planejamentoService) {
        this.planejamentoService = planejamentoService;
    }

    @GetMapping("/{id}")
    public String exibirPlano(@PathVariable UUID id, Model model) {
        // 1. Busca os dados do plano (nome, cargo, etc.)
        PlanejamentoDTO plano = planejamentoService.findOne(id);

        // 2. Gera o ciclo de estudos usando nosso novo método
        List<SessaoEstudoDTO> ciclo = planejamentoService.gerarCicloDeEstudos(id);

        // 3. Envia os dados para a página HTML
        model.addAttribute("plano", plano);
        model.addAttribute("ciclo", ciclo);

        return "telaPlano"; // Renderiza a página telaPlano.html
    }
}