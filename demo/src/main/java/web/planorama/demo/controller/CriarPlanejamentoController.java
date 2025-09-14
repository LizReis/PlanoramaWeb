package web.planorama.demo.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


import org.springframework.ui.Model;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import web.planorama.demo.dto.MateriaDTO;
import web.planorama.demo.dto.MateriaPlanejamentoDTO;
import web.planorama.demo.dto.PlanejamentoDTO;
import web.planorama.demo.dto.UsuarioDTO;
import web.planorama.demo.entity.MateriaPlanejamentoEntity;
import web.planorama.demo.entity.UsuarioEntity;
import web.planorama.demo.mapping.MateriaMapper;
import web.planorama.demo.mapping.MateriaPlanejamentoMapper;
import web.planorama.demo.mapping.UsuarioMapper;
import web.planorama.demo.repository.UsuarioRepository;
import web.planorama.demo.service.MateriaService;
import web.planorama.demo.service.PlanejamentoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/criar-planejamento")
@RequiredArgsConstructor
@Slf4j
@SessionAttributes("planejamentoDTO")
public class CriarPlanejamentoController {

    private final PlanejamentoService planejamentoService;
    private final MateriaService materiaService;

    private final UsuarioMapper usuarioMapper;

    private final UsuarioRepository usuarioRepository;

    // Temos que criar um DTO vazio para que ele seja usado ao longo das telas de
    // criação
    // do planejamento, ele guardará os dados para criação.
    // Toda vez que o usuário clicar em criar ou salvar dos cards, ele guardará e
    // redirecionará
    // para o próximo
    @ModelAttribute("planejamentoDTO")
    public PlanejamentoDTO planejamentoDTO() {
        return new PlanejamentoDTO();
    }

    @GetMapping
    public String getPrimeiroCard(Model model) {
        model.addAttribute("planejamentoDTO", new PlanejamentoDTO());
        return "primeiroCriarPlano :: cardCriacao";
    }

    @GetMapping("/segundo-card")
    public String getSegundoCard(Model model) {
        List<MateriaDTO> materias = materiaService.findAll();
        model.addAttribute("materias", materias);

        return "segundoCriarPlano :: cardCriacao";
    }

    @GetMapping("/finalizar")
    public String getTerceiroCard(Model model) {
        return "terceiroCriarPlano :: cardCriacao";
    }

    @PostMapping("/primeiro-card")
    public String postPrimeiroCard(@Valid @ModelAttribute("planejamentoDTO") PlanejamentoDTO planejamentoDTO,
            BindingResult result, Model model) {
        log.info("Request: {} {}", planejamentoDTO, result.hasErrors());
        if (result.hasErrors()) {
            model.addAttribute("planejamentoDTO", planejamentoDTO);
            log.error("Erros encontrados: {}", result.getAllErrors());

            return "primeiroCriarPlano :: cardCriacao";
        }

        List<MateriaDTO> materias = materiaService.findAll();
        model.addAttribute("materias", materias);

        // Senão tiver erros, ele apenas pega as coisas possíveis no planejamentodto
        // vazio
        // e redireciona para o próximo card
        return "segundoCriarPlano :: cardCriacao";
    }

    @PostMapping("/segundo-card")
    public String postSegundoCard(@ModelAttribute("planejamentoDTO") PlanejamentoDTO planejamentoDTO, Model model,
            @RequestParam(value = "materiaIds", required = false) List<UUID> materiaIds) {

        if(materiaIds == null || materiaIds.isEmpty()){
            model.addAttribute("error", "Você deve selecionar, pelo menos, uma matéria para o Planejamento.");
            
            List<MateriaDTO> materias = materiaService.findAll();
            model.addAttribute("materias", materias);

            return "segundoCriarPlano :: cardCriacao";
        }

        List<MateriaPlanejamentoDTO> materiasSelecionadas = materiaIds.stream().map(id -> {
            MateriaDTO materia = materiaService.findById(id);
            MateriaPlanejamentoDTO materiaPlanejamentoDTO = new MateriaPlanejamentoDTO();

            materiaPlanejamentoDTO.setMateriaDTO(materia);
            materiaPlanejamentoDTO.setIdMateriaDTO(materia.getId());

            return materiaPlanejamentoDTO;
        }).collect(Collectors.toList());

        planejamentoDTO.setMaterias(materiasSelecionadas);

        model.addAttribute("planejamentoDTO", planejamentoDTO);

        return "terceiroCriarPlano :: cardCriacao";
    }

    @PostMapping("/finalizar")
    public String postFinalizarCriacao(@Valid @ModelAttribute("planejamentoDTO") PlanejamentoDTO planejamentoDTO, BindingResult result, SessionStatus status,  Model model) {
        log.info("Request: {}", planejamentoDTO);

        if(result.hasErrors()){
            log.error("Erros de validação encontrados ao finalizar: {}", result.getAllErrors());

            model.addAttribute("planejamentoDTO", planejamentoDTO);

            return "terceiroCriarPlano :: cardCriacao";
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String emailUsuarioLogado = ((UserDetails) principal).getUsername();

        UsuarioEntity criador = usuarioRepository.findByEmail(emailUsuarioLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado para finalização do plano."));

        planejamentoDTO.setCriador(usuarioMapper.toUsuarioDTO(criador));

        PlanejamentoDTO planejamentoSalvo = planejamentoService.save(planejamentoDTO);

        status.setComplete();

        return "redirect:/planejamento/" + planejamentoSalvo.getId();
    }

}
