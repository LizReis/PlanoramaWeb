package web.planorama.demo.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import web.planorama.demo.dto.MateriaDTO;
import web.planorama.demo.dto.MateriaPlanejamentoDTO;
import web.planorama.demo.dto.PlanejamentoDTO;
import web.planorama.demo.entity.MateriaPlanejamentoEntity;
import web.planorama.demo.mapping.MateriaMapper;
import web.planorama.demo.mapping.MateriaPlanejamentoMapper;
import web.planorama.demo.service.MateriaService;
import web.planorama.demo.service.PlanejamentoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;



@Controller
@RequestMapping("/criar-planejamento")
@RequiredArgsConstructor
@Slf4j
@SessionAttributes("planejamentoDTO")
public class CriarPlanejamentoController {

    private final PlanejamentoService planejamentoService;
    private final MateriaService materiaService;
    private final MateriaMapper materiaMapper;
    private final MateriaPlanejamentoMapper materiaPlanejamentoMapper;


    //Temos que criar um DTO vazio para que ele seja usado ao longo das telas de criação
    //do planejamento, ele guardará os dados para criação.
    //Toda vez que o usuário clicar em criar ou salvar dos cards, ele guardará e redirecionará
    //para o próximo
    @ModelAttribute("planejamentoDTO")
    public PlanejamentoDTO planejamentoDTO(){
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
    
    
    @PostMapping("/primeiro-card")
    public String postPrimeiroCard(@Valid @ModelAttribute("planejamentoDTO") PlanejamentoDTO planejamentoDTO, BindingResult result, Model model) {
        log.info("Request: {} {}", planejamentoDTO, result.hasErrors());
        if (result.hasErrors()) {
            model.addAttribute("planejamentoDTO", planejamentoDTO);
            log.error("Erros encontrados: {}", result.getAllErrors());

            return "primeiroCriarPlano :: cardCriacao";
        }

        //Senão tiver erros, ele apenas pega as coisas possíveis no planejamentodto vazio
        //e redireciona para o próximo card
        return "segundoCriarPlano :: cardCriacao";
    }
    

    @PostMapping("/segundo-card")
    public String postSegundoCard(@Valid @ModelAttribute("planejamentoDTO") PlanejamentoDTO planejamentoDTO, BindingResult result, Model model, @RequestParam("materiaIds") List<UUID> materiaIds) {
        
        List<MateriaPlanejamentoDTO> materiasSelecionadas = materiaIds.stream().map(id -> {
            MateriaDTO materia = materiaService.findById(id);
            MateriaPlanejamentoDTO materiaPlanejamentoDTO = new MateriaPlanejamentoDTO();

            materiaPlanejamentoDTO.setMateriaEntity(materiaMapper.toMateriaEntity(materia));

            return materiaPlanejamentoDTO;
        }).collect(Collectors.toList());


        List<MateriaPlanejamentoEntity> materiasSelecionadasEntity = materiasSelecionadas.stream().map(m -> {
           return materiaPlanejamentoMapper.toMateriaPlanejamentoEntity(m);
        }).collect(Collectors.toList());

        planejamentoDTO.setMaterias(materiasSelecionadasEntity);

        return "redirect:/criar-planejamento/terceiro-card";
    }
    
}

package web.planorama.demo.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import web.planorama.demo.dto.MateriaDTO;
import web.planorama.demo.dto.MateriaPlanejamentoDTO;
import web.planorama.demo.dto.PlanejamentoDTO;
import web.planorama.demo.entity.MateriaPlanejamentoEntity;
import web.planorama.demo.entity.PlanejamentoEntity;
import web.planorama.demo.entity.UsuarioEntity;
import web.planorama.demo.mapping.MateriaMapper;
import web.planorama.demo.mapping.MateriaPlanejamentoMapper;
import web.planorama.demo.mapping.PlanejamentoMapper;
import web.planorama.demo.repository.PlanejamentoRepository;
import web.planorama.demo.service.MateriaService;

@Controller
@RequestMapping("/criar-planejamento")
@RequiredArgsConstructor
@Slf4j
@SessionAttributes("planejamentoDTO")
public class CriarPlanejamentoController {

    private final PlanejamentoRepository planejamentoRepository;
    private final MateriaService materiaService;
    private final MateriaMapper materiaMapper;
    private final MateriaPlanejamentoMapper materiaPlanejamentoMapper;
    private final PlanejamentoMapper planejamentoMapper;

    @ModelAttribute("planejamentoDTO")
    public PlanejamentoDTO planejamentoDTO() {
        return new PlanejamentoDTO();
    }

    // --- MÉTODOS CORRIGIDOS PARA USAR A PÁGINA HOSPEDEIRA ---

    @GetMapping
    public String getPrimeiroCard(Model model) {
        model.addAttribute("planejamentoDTO", new PlanejamentoDTO());
        // Informa qual fragmento carregar
        model.addAttribute("cardFragmento", "primeiroCriarPlano :: cardCriacao");
        // Renderiza a página principal
        return "criarPlano";
    }

    @PostMapping("/primeiro-card")
    public String postPrimeiroCard(@Valid @ModelAttribute("planejamentoDTO") PlanejamentoDTO planejamentoDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Se houver erro, recarrega a página principal com o mesmo fragmento
            model.addAttribute("cardFragmento", "primeiroCriarPlano :: cardCriacao");
            return "criarPlano";
        }
        return "redirect:/criar-planejamento/segundo-card";
    }

    @GetMapping("/segundo-card")
    public String getSegundoCard(Model model) {
        List<MateriaDTO> materias = materiaService.findAll();
        model.addAttribute("materias", materias);
        // Informa qual fragmento carregar
        model.addAttribute("cardFragmento", "segundoCriarPlano :: cardCriacao");
        // Renderiza a página principal
        return "criarPlano";
    }

    @PostMapping("/segundo-card")
    public String postSegundoCard(@ModelAttribute("planejamentoDTO") PlanejamentoDTO planejamentoDTO, @RequestParam(value = "materiaIds", required = false) List<UUID> materiaIds) {
        if (materiaIds == null || materiaIds.isEmpty()) {
            return "redirect:/criar-planejamento/segundo-card";
        }

        List<MateriaPlanejamentoEntity> materiasSelecionadas = materiaIds.stream().map(id -> {
            MateriaDTO materia = materiaService.findById(id);
            MateriaPlanejamentoDTO dto = new MateriaPlanejamentoDTO();
            dto.setMateriaEntity(materiaMapper.toMateriaEntity(materia));
            return materiaPlanejamentoMapper.toMateriaPlanejamentoEntity(dto);
        }).collect(Collectors.toList());

        planejamentoDTO.setMaterias(materiasSelecionadas);
        return "redirect:/criar-planejamento/terceiro-card";
    }

    @GetMapping("/terceiro-card")
    public String getTerceiroCard(@ModelAttribute("planejamentoDTO") PlanejamentoDTO planejamentoDTO, Model model) {
        model.addAttribute("planejamentoDTO", planejamentoDTO);
        // Informa qual fragmento carregar
        model.addAttribute("cardFragmento", "terceiroCriarPlano :: cardCriacao");
        // Renderiza a página principal
        return "criarPlano";
    }

    @PostMapping("/finalizar")
    public String finalizarPlanejamento(
            @ModelAttribute("planejamentoDTO") PlanejamentoDTO planejamentoDTO,
            @AuthenticationPrincipal UsuarioEntity usuarioLogado,
            SessionStatus status) {

        PlanejamentoEntity planejamento = planejamentoMapper.toPlanejamentoEntity(planejamentoDTO);
        planejamento.setCriador(usuarioLogado);

        if (planejamento.getMaterias() != null) {
            planejamento.getMaterias().forEach(materia -> materia.setPlanejamentoEntity(planejamento));
        }

        PlanejamentoEntity savedPlano = planejamentoRepository.save(planejamento);
        status.setComplete();

        // Redireciona para a nova tela do plano, que vamos criar a seguir
        return "redirect:/planejamento/" + savedPlano.getIdPlanejamento();
    }
}