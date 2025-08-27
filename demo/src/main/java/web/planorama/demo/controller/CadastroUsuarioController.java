package web.planorama.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; 
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import web.planorama.demo.dto.EstudanteDTO;
import web.planorama.demo.service.ArquivosUsuarioService;
import web.planorama.demo.service.EstudanteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;



@Controller
@RequestMapping("/cadastro")
@RequiredArgsConstructor
@Slf4j
public class CadastroUsuarioController {
    private final EstudanteService estudanteService;
    private final ArquivosUsuarioService arquivosUsuarioService;

    @GetMapping
    public String pagina(Model model){
        model.addAttribute("estudante", new EstudanteDTO(null, "", "", "", null, null));

        return "cadastro";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("estudante") EstudanteDTO estudanteDTO, BindingResult result, @RequestParam("fotoFile") MultipartFile fotoFile, Model model) {
        if(result.hasErrors()){
            log.error("Erros encontrados: {}", result.getAllErrors());
            
            return "cadastro";
        }

        String fotoPath = arquivosUsuarioService.saveFile(fotoFile);

        EstudanteDTO dtoComFoto = new EstudanteDTO(
            estudanteDTO.id(),
            estudanteDTO.nome(),
            estudanteDTO.email(),
            estudanteDTO.senha(),
            fotoPath, //Temos que salvar o dto assim porque a foto tem que ser salva no path
            estudanteDTO.descricaoUsuario()
        );

        log.info("Request: {}", dtoComFoto);
        estudanteService.save(dtoComFoto);
        return "redirect:/login";
    }



    //O CADASTRO DO ADM AQUI NÃO É MAIS NECESSÁRIO
    /* 
    @PostMapping("/adm")
    public String create(AdministradorDTO administradorDTO, @RequestParam("fotoFile") MultipartFile fotoFile){

        String fotoPath = arquivosUsuarioService.saveFile(fotoFile);

        AdministradorDTO dtoComFoto = new AdministradorDTO(
            administradorDTO.id(),
            administradorDTO.nome(),
            administradorDTO.email(),
            administradorDTO.senha(),
            fotoPath,
            administradorDTO.descricaoUsuario()
        );

        log.info("Request: {}", dtoComFoto);
        admService.save(dtoComFoto);
        return "redirect:/login";
    }*/
}
