package web.planorama.demo.controller;

// ... imports
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import web.planorama.demo.dto.PlanejamentoDTO;
import web.planorama.demo.dto.SessaoEstudoDTO;
import web.planorama.demo.service.PlanejamentoService;

import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/planejamento")
@RequiredArgsConstructor
public class PlanejamentoController {

    private final PlanejamentoService planejamentoService;

    @GetMapping("/{id}")
    public String exibirPlano(@PathVariable String id, Model model) {

        UUID idUUID;

        try{
            idUUID = UUID.fromString(id);
        }catch(IllegalArgumentException e){
            return "paginaErro";
        }

        PlanejamentoDTO planejamento = planejamentoService.findOne(idUUID);

        model.addAttribute("planejamento", planejamento);

        List<SessaoEstudoDTO> sessoesPlanejamento = planejamentoService.buscarCicloEstudo(idUUID);
        model.addAttribute("sessoes", sessoesPlanejamento);

        return "telaPlano";
    }

    @PostMapping("/arquivar/{id}")
    public String postMethodName(@PathVariable String id) {
        UUID idUUID;

        try{
            idUUID = UUID.fromString(id);
        }catch(IllegalArgumentException e){
            return "paginaErro";
        }

        PlanejamentoDTO planejamentoParaArquivar = planejamentoService.findOne(idUUID);

        planejamentoService.arquivarPlanoDeEstudos(planejamentoParaArquivar);

        return "arquivoPlano";
    }
 
}