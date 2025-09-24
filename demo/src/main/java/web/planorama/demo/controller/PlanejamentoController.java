package web.planorama.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
// ... imports
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import web.planorama.demo.dto.PlanejamentoDTO;
import web.planorama.demo.dto.SessaoEstudoDTO;
import web.planorama.demo.dto.UsuarioDTO;
import web.planorama.demo.service.PlanejamentoService;

import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/planejamento")
@RequiredArgsConstructor
public class PlanejamentoController {

    private final PlanejamentoService planejamentoService;

    @GetMapping("/{id}")
    public String exibirPlano(@PathVariable String id, Model model) {

        UUID idUUID;

        try {
            idUUID = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return "paginaErro";
        }

        PlanejamentoDTO planejamento = planejamentoService.findOne(idUUID);

        model.addAttribute("planejamento", planejamento);

        List<SessaoEstudoDTO> sessoesPlanejamento = planejamentoService.buscarCicloEstudo(idUUID);
        model.addAttribute("sessoes", sessoesPlanejamento);

        return "telaPlano";
    }

    @GetMapping("/editar/{id}")
    public String cardEditarPlanejamento(@PathVariable String id, Model model) {
        try {
            UUID idUUID = UUID.fromString(id);
            PlanejamentoDTO planejamento = planejamentoService.findOne(idUUID);
            model.addAttribute("planejamentoParaAlterar", planejamento);
        } catch (Exception e) {
            return "redirect:/home";
        }
        return "alterarPlano :: cardAlteracao";
    }

    @PostMapping("/arquivar/{id}")
    public String arquivarPlanejamento(@PathVariable String id, RedirectAttributes redirectAttributes) {
        UUID idUUID;

        try {
            idUUID = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return "paginaErro";
        }

        try {
            PlanejamentoDTO planejamentoParaArquivar = planejamentoService.findOne(idUUID);

            planejamentoService.arquivarPlanoDeEstudos(planejamentoParaArquivar);
            redirectAttributes.addFlashAttribute("success", "Planejamento arquivado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/home";
    }

    @PostMapping("/remover/{id}")
    public String removerPlanejamento(@PathVariable String id, RedirectAttributes redirectAttributes) {
        UUID idUUID;

        try {
            idUUID = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return "paginaErro";
        }

        try {
            PlanejamentoDTO planejamentoParaRemover = planejamentoService.findOne(idUUID);

            planejamentoService.remove(planejamentoParaRemover.getId());
            redirectAttributes.addFlashAttribute("success", "Planejamento removido com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/home";
    }

    @PostMapping("/editar/{id}")
    public String postMethodName(@PathVariable("id") String idPlanejamento,
            @RequestParam("novoNome") String novoNomePlano,
            @RequestParam("novoCargo") String novoCargo,
            @RequestParam("novaCargaHoraria") String novaCargaHoraria,
            @RequestParam("novoAnoAplicacao") String novoAnoAplicacao, RedirectAttributes redirectAttributes) {

        if (novoNomePlano.isBlank() || novoNomePlano.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "O campo de Novo Nome do Planejamento não pode estar vazio.");
            return "redirect:/home";
        } else if (novoCargo.isBlank() || novoCargo.isEmpty()) {
            redirectAttributes.addFlashAttribute("error",
                    "O campo de Novo Cargo do Planejamento não pode estar vazio.");
            return "redirect:/home";
        } else if (novaCargaHoraria.isBlank() || novaCargaHoraria.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Adicione corretamente a Carga Horária");
            return "redirect:/home";
        } else if (novoAnoAplicacao.isBlank() || novoAnoAplicacao.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Adicione um ano de aplicação válido");
            return "redirect:/home";
        } else if (novoNomePlano.isEmpty() && novoCargo.isEmpty() && novaCargaHoraria.isEmpty()
                && novoAnoAplicacao.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Todos os campos devem ser preenchidos.");
            return "redirect:/home";
        } else {
            try {
                UUID idUUID = UUID.fromString(idPlanejamento);
                PlanejamentoDTO planejamentoAlterar = planejamentoService.findOne(idUUID);

                planejamentoAlterar.setNomePlanejamento(novoNomePlano);
                planejamentoAlterar.setCargo(novoCargo);
                planejamentoAlterar.setHorasDiarias(Integer.parseInt(novaCargaHoraria));
                planejamentoAlterar.setAnoAplicacao(Integer.parseInt(novoAnoAplicacao));

                planejamentoService.save(planejamentoAlterar);
                redirectAttributes.addFlashAttribute("success", "Planejamento atualizado com sucesso!");

            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "Erro ao atualizar o planejamento.");
            }

            return "redirect:/home";
        }
    }

}