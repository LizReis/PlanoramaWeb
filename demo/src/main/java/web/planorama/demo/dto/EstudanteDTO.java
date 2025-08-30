package web.planorama.demo.dto;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


public record EstudanteDTO(UUID id,

                @NotBlank 
                @NotNull
                String nome, 

                @NotBlank
                @NotNull
                @Email
                String email, 

                @NotBlank
                @NotNull
                String senha, 

                String fotoUsuario, 

                String descricaoUsuario) {

}
