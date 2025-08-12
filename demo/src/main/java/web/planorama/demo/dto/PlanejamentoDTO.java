package web.planorama.demo.dto;

import java.util.UUID;

import web.planorama.demo.entity.UsuarioEntity;

import java.util.ArrayList;

public record PlanejamentoDTO(UUID id, String nomePlanejamento, String cargo, int anoAplicacao, ArrayList<String> disponibilidade, ArrayList<String> materias, UsuarioEntity usuario, boolean planoArquivado, boolean preDefinidoAdm) {

}
