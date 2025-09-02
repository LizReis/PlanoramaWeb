package web.planorama.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.planorama.demo.dto.PlanejamentoDTO;
import web.planorama.demo.service.PlanejamentoService;

@RestController
@RequestMapping("/api/planejamentos")
public class PlanejamentoRestController {

    private final PlanejamentoService planejamentoService;

    public PlanejamentoRestController(PlanejamentoService planejamentoService) {
        this.planejamentoService = planejamentoService;
    }

    //Recebe o JSON do frontend, converte para DTO e passa para o serviço.

    @PostMapping
    public ResponseEntity<PlanejamentoDTO> criarNovoPlano(@RequestBody PlanejamentoDTO planejamentoDTO) {
        try {
            PlanejamentoDTO planoSalvo = planejamentoService.save(planejamentoDTO);
            return new ResponseEntity<>(planoSalvo, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}