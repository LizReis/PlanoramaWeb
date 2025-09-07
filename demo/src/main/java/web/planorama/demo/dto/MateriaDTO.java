package web.planorama.demo.dto;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MateriaDTO {
    private UUID id;

    @NotNull
    @NotBlank
    private String nomeMateria;

    private UsuarioDTO criadoPor;

    @NotBlank
    @NotNull
    private List<AssuntoDTO> listaAssuntos;

    private List<SessaoEstudoDTO>listaSessao;
    private List<MateriaPlanejamentoDTO> planejamentosComMateria;

}
