package web.planorama.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PapeisUsuario {

    ADMIN(1L), ESTUDANTE(2L);
    private Long id;
}
