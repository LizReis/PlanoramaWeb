package web.planorama.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import web.planorama.demo.dto.AssuntoDTO;
import web.planorama.demo.dto.MateriaDTO;
import web.planorama.demo.entity.AssuntoEntity;
import web.planorama.demo.mapping.AssuntoMapper;
import web.planorama.demo.service.AssuntoService;
import web.planorama.demo.service.MateriaService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;




@Controller
@RequestMapping("/materia")
@RequiredArgsConstructor
@Slf4j
public class MateriaController {

    private final MateriaService materiaService;
    private final AssuntoService assuntoService;
    private final AssuntoMapper assuntoMapper;

    @GetMapping
    public String getCardSalvarMateria() {
        return "criarMateria :: cardCriacao";
    }
    

    @PostMapping("/salvar")
    public String salvarMateria(@RequestParam("nomeMateria") String nomeMateria, @RequestParam("assuntos") List<String> listaAssuntos, Model model) {

        List<AssuntoEntity> assuntos = listaAssuntos.stream().map(assunto ->{
            AssuntoDTO assuntoDTO =  new AssuntoDTO();

            assuntoDTO.setNomeAssunto(assunto);
            return assuntoMapper.toAssuntoEntity(assuntoDTO);
        }).collect(Collectors.toList());

        materiaService.save(nomeMateria, assuntos);//salvo as materias

        List<MateriaDTO> listaMaterias = materiaService.findAll();//crio uma lista com todas as materias do sistema

        model.addAttribute("materias", listaMaterias);//coloco no model para ser usado pelo retorno ao segundoCriarPlano
        
        return "segundoCriarPlano :: cardCriacao";
    }
    
}
