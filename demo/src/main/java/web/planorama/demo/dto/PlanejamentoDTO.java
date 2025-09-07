package web.planorama.demo.dto;

import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanejamentoDTO{

    private UUID id;

    @NotBlank(message = "Digite um nome para o Planejamento")
    @NotNull
    private String nomePlanejamento;

    @NotBlank(message = "Digite um nome para o Cargo do planejamento")
    @NotNull
    private String cargo;

    @NotNull
    @Min(value = 2025, message = "O ano de aplicação deve ser 2025 ou superior.")
    private int anoAplicacao; 

    @NotNull(message = "Defina os dias que você terá disponibilidade")
    private List<String> disponibilidade;


    @NotNull 
    @Min(value = 1, message = "Deve ser no mínimo 1 hora por dia.")
    private int horasDiarias;

    @Valid
    private List<MateriaPlanejamentoDTO> materias;

    private UsuarioDTO criador;

    @NotNull 
    private boolean planoArquivado;

    @NotNull
    private boolean preDefinidoAdm;
}
