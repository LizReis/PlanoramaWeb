package web.planorama.demo.dto;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrarEstudoDTO{
    private UUID id;
    @NotNull(message = "O assunto não pode ser nulo, selecione, pelo menos um.")
    private UUID assuntoId;
    @NotNull(message = "O campo das horas não pode ser nulo.")
    @Min(value = 0, message = "O valor mínimo para as horas é 0.")
    private Integer horasEstudadas;
    @NotNull(message = "O campo dos minutos não pode ser nulo.")
    @Min(value = 1, message = "Você precisa registrar, pelo menos, 1min de estudo.")
    private Integer minutosEstudados;
    @NotNull
    private UUID planejamentoId;
    @NotNull
    private UUID idMateriaPlanejamento;


}
