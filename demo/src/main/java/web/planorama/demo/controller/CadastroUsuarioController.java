package web.planorama.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

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
import web.planorama.demo.repository.EstudanteRepository;
import web.planorama.demo.service.EstudanteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequestMapping("/cadastro")
@RequiredArgsConstructor
@Slf4j
public class CadastroUsuarioController {

    private final EstudanteRepository estudanteRepository;
    private final EstudanteService estudanteService;

    private final String UPLOAD_DIR = "uploadsUser";

    @GetMapping
    public String pagina(Model model) {
        model.addAttribute("estudante", new EstudanteDTO(null, "", "", "", null, null));

        return "cadastro";
    }

    @GetMapping("/login")
    public String getTelaLogin() {
        return "login";
    }
    

    @PostMapping
    public String create(@Valid @ModelAttribute("estudante") EstudanteDTO estudanteDTO, BindingResult result,
            @RequestParam("fotoFile") MultipartFile fotoUsuario, Model model) throws IOException {
        log.info("Request: {} {}", estudanteDTO, result.hasErrors());
        if (result.hasErrors()) {
            model.addAttribute("estudante", estudanteDTO);
            log.error("Erros encontrados: {}", result.getAllErrors());

            return "cadastro";
        }

        EstudanteDTO estudanteParaSalvar = estudanteDTO;

        if(fotoUsuario != null && !fotoUsuario.isEmpty()){
            String fileName = UUID.randomUUID() + "_" + fotoUsuario.getOriginalFilename();

            Path caminhoArquivo = Paths.get(UPLOAD_DIR, fileName);

            Files.createDirectories(caminhoArquivo.getParent()); //cria pasta se não existir

            //Salva no sistema de arquivos
            Files.copy(fotoUsuario.getInputStream(), caminhoArquivo, StandardCopyOption.REPLACE_EXISTING);

            String UrlFile = "/uploadsUser/" + fileName;

            //Como o meu DTO é um record ele é imutável, então preciso dar um new no DTO para salvar a imagem
            estudanteParaSalvar = new EstudanteDTO(
                estudanteDTO.id(),
                estudanteDTO.nome(),
                estudanteDTO.email(),
                estudanteDTO.senha(),
                UrlFile, // <- Inserindo o nome do arquivo aqui!
                estudanteDTO.descricaoUsuario()
            );
        }

        estudanteService.save(estudanteParaSalvar);
        return "redirect:/login";
    }

    private void loadFormData(Model model) {
        model.addAttribute("estudantes", estudanteService.findAll());
        model.addAttribute("estudante", new EstudanteDTO(null, null, null, null, null, null));
    }
}
