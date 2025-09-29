package web.planorama.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import web.planorama.demo.dto.PlanejamentoDTO;
import web.planorama.demo.repository.PlanejamentoRepository;
import web.planorama.demo.service.PlanejamentoService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/arquivo")
@RequiredArgsConstructor
@Slf4j
public class ArquivoPlanoController {

    private final PlanejamentoService planejamentoService;

    //GET PARA MOSTRAR TODOS OS PLANEJAMENTOS QUE ESTÃO MARCADOS COMO ARQUIVADO DO USUÁRIO
    @GetMapping()
    public String getMethodName(Model model) {
        List<Object> allPlanejamentosArquivados = new ArrayList<>();

        allPlanejamentosArquivados.addAll(planejamentoService.findAllPlanejamentoIsArquivado());
        
        model.addAttribute("allPlanejamentosArquivados", allPlanejamentosArquivados);

        return "arquivoPlano";
    }

    //POST PARA DESARQUIVAR
    @PostMapping("/desarquivar/{id}")
    public String desarquivarPlanejamento(@PathVariable String id, RedirectAttributes redirectAttributes) {
        UUID idUUID;

        try{
            idUUID = UUID.fromString(id);
        }catch(IllegalArgumentException e){
            return "paginaErro";
        }

        try{
            PlanejamentoDTO planejamentoParaArquivar = planejamentoService.findOne(idUUID);

            planejamentoService.desarquivarPlanoDeEstudos(planejamentoParaArquivar);
            redirectAttributes.addFlashAttribute("success", "Planejamento desarquivado com sucesso!");
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        

        return "redirect:/arquivo";
    }
    
    //POST PARA REMOVER UM PLANEJAMENTO 
    @PostMapping("/remover/{id}")
    public String removerPlanejamento(@PathVariable String id, RedirectAttributes redirectAttributes) {
        UUID idUUID;

        try{
            idUUID = UUID.fromString(id);
        }catch(IllegalArgumentException e){
            return "paginaErro";
        }

        try{
            PlanejamentoDTO planejamentoParaRemover = planejamentoService.findOne(idUUID);

            planejamentoService.remove(planejamentoParaRemover.getId());
            redirectAttributes.addFlashAttribute("success", "Planejamento removido com sucesso!");
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/arquivo";
    }
}
