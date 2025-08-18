package web.planorama.demo.dto;

import java.util.UUID;

import web.planorama.demo.entity.UsuarioEntity;

import java.util.ArrayList;

public record PlanejamentoDTO(UUID id, String nomePlanejamento, String cargo, int anoAplicacao, ArrayList<String> disponibilidade, int horasDiarias, ArrayList<String> materias, UsuarioEntity criador, boolean planoArquivado, boolean preDefinidoAdm) {

}
