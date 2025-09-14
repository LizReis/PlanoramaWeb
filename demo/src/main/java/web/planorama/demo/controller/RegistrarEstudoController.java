package web.planorama.demo.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import web.planorama.demo.dto.AssuntoDTO;
import web.planorama.demo.dto.MateriaDTO;
import web.planorama.demo.dto.RegistrarEstudoDTO;
import web.planorama.demo.dto.SessaoEstudoDTO;
import web.planorama.demo.exceptions.MyNotFoundException;
import web.planorama.demo.mapping.AssuntoMapper;
import web.planorama.demo.mapping.MateriaMapper;
import web.planorama.demo.repository.AssuntoRepository;
import web.planorama.demo.service.AssuntoService;
import web.planorama.demo.service.MateriaService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
@Slf4j
public class RegistrarEstudoController {

    private final MateriaService materiaService;
    private final AssuntoRepository assuntoRepository;

    private final MateriaMapper materiaMapper;
    private final AssuntoMapper assuntoMapper;

    @GetMapping("/registrar-estudo/{idMateria}")
    public String getCardRegistrarEstudo(@PathVariable UUID idMateria, Model model) {
        try{

            MateriaDTO materiaSelecionada = materiaService.findById(idMateria);
            List<AssuntoDTO> assuntosMateria = assuntoRepository.findAllByMateriaEntity(materiaMapper.toMateriaEntity(materiaSelecionada)).stream().map(assunto -> {
                AssuntoDTO assuntoDTO = assuntoMapper.toAssuntoDTO(assunto);
                return assuntoDTO;
            }).collect(Collectors.toList());

            model.addAttribute("listaAssuntos", assuntosMateria);
            model.addAttribute("registroEstudo", new RegistrarEstudoDTO());

        }catch(MyNotFoundException e){
            model.addAttribute("erroNotFound", e.getMessage());
        }

        return "registrarEstudo :: cardCriacao";
    }
    

}
