package web.planorama.demo.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import web.planorama.demo.dto.AssuntoDTO;
import web.planorama.demo.dto.MateriaDTO;
import web.planorama.demo.dto.MateriaPlanejamentoDTO;
import web.planorama.demo.dto.PlanejamentoDTO;
import web.planorama.demo.dto.RegistrarEstudoDTO;
import web.planorama.demo.entity.MateriaEntity;
import web.planorama.demo.entity.MateriaPlanejamentoEntity;
import web.planorama.demo.exceptions.MyNotFoundException;
import web.planorama.demo.mapping.AssuntoMapper;
import web.planorama.demo.mapping.MateriaMapper;
import web.planorama.demo.mapping.MateriaPlanejamentoMapper;
import web.planorama.demo.repository.AssuntoRepository;
import web.planorama.demo.repository.MateriaPlanejamentoRepository;
import web.planorama.demo.service.MateriaPlanejamentoService;
import web.planorama.demo.service.MateriaService;
import web.planorama.demo.service.PlanejamentoService;
import web.planorama.demo.service.RegistrarEstudoService;


@Controller
@RequiredArgsConstructor
@Slf4j
public class RegistrarEstudoController {

    private final MateriaService materiaService;
    private final RegistrarEstudoService registrarEstudoService;
    private final PlanejamentoService planejamentoService;
    private final MateriaPlanejamentoService materiaPlanejamentoService;

    private final AssuntoRepository assuntoRepository;
    private final MateriaPlanejamentoRepository materiaPlanejamentoRepository;

    private final MateriaMapper materiaMapper;
    private final AssuntoMapper assuntoMapper;
    private final MateriaPlanejamentoMapper materiaPlanejamentoMapper;

    @GetMapping("/registrar-estudo/{idMateriaPlanejamento}/{idPlanejamento}")
    public String getCardRegistrarEstudo(@PathVariable UUID idMateriaPlanejamento,
                                         @PathVariable UUID idPlanejamento, Model model) {
        try {
            MateriaPlanejamentoEntity mpEntity = materiaPlanejamentoRepository.findById(idMateriaPlanejamento)
                    .orElseThrow(() -> new MyNotFoundException("Vínculo Matéria-Planejamento não encontrado."));

            MateriaEntity materia = mpEntity.getMateriaEntity();

            List<AssuntoDTO> assuntosDTO = assuntoRepository.findAllByMateriaEntity(materia)
                    .stream()
                    .map(assuntoMapper::toAssuntoDTO)
                    .collect(Collectors.toList());

            RegistrarEstudoDTO registroEstudo = new RegistrarEstudoDTO();
            registroEstudo.setPlanejamentoId(idPlanejamento);
            registroEstudo.setIdMateriaPlanejamento(idMateriaPlanejamento);

            model.addAttribute("listaAssuntos", assuntosDTO);
            model.addAttribute("registroEstudo", registroEstudo);
            model.addAttribute("materia", materiaMapper.toMateriaDTO(materia));

        } catch (Exception e) {
            log.error("### Erro ao buscar dados para o card de registro de estudo ###", e);
            model.addAttribute("erroNotFound", "Não foi possível carregar os dados para o registro.");
            // Lógica para evitar que o template quebre em caso de erro
            model.addAttribute("materia", new MateriaDTO());
            model.addAttribute("registroEstudo", new RegistrarEstudoDTO());
            model.addAttribute("listaAssuntos", new java.util.ArrayList<>());
        }

        return "registrarEstudo :: cardCriacao";
    }
    

    @PostMapping("/registrar-estudo/registrar")
    public String postRegistrarEstudo(@Valid @ModelAttribute("registroEstudo") RegistrarEstudoDTO registroEstudoDTO, 
        BindingResult result, RedirectAttributes redirectAttributes) {
        
        log.info("Request: {} {}", registroEstudoDTO, result.hasErrors());
        if (result.hasErrors()) {
            log.error("Erros encontrados: {}", result.getAllErrors());
            redirectAttributes.addFlashAttribute("error", "Erro ao recarregar os dados do formulário.");
            return "redirect:/planejamento/" + registroEstudoDTO.getPlanejamentoId();
        }

        try{
            registrarEstudoService.save(registroEstudoDTO);
            redirectAttributes.addFlashAttribute("success", "Estudo registrado com sucesso.");
        } catch(MyNotFoundException e){
            redirectAttributes.addFlashAttribute("error", "Ocorreu um erro ao tentar registrar o estudo. Tente novamente");
        }
        
        UUID planejamentoId = registroEstudoDTO.getPlanejamentoId();
        return "redirect:/planejamento/" + planejamentoId;
    }

}
