package web.planorama.demo.service;

import java.util.List;
import java.util.UUID;

import web.planorama.demo.dto.SessaoEstudoDTO;
import web.planorama.demo.dto.UsuarioDTO;
import web.planorama.demo.entity.UsuarioEntity;
import web.planorama.demo.dto.PlanejamentoDTO;
import web.planorama.demo.dto.PlanejamentoProgressDTO;

public interface PlanejamentoService {
    PlanejamentoDTO save(PlanejamentoDTO planejamentoDTO);
    PlanejamentoDTO findOne(UUID id);
    List<PlanejamentoDTO> findAll(); //Aqui encontra todos os planejamentos existentes
    List<PlanejamentoDTO> findAllOfAdm(); //Aqui encontra todos os planejamento criados por Administadores
    List<PlanejamentoDTO> findAllOfEstudante(UsuarioDTO usuarioDTO); //Aqui encontra todos os planejamentos de um determinado Estudante
    List<PlanejamentoDTO> findAllPlanejamentoIsArquivado(); //Pega todos os planos que foram arquvivados pelo usuário logado
    List<PlanejamentoProgressDTO> findAllComProgressoByUsuario(UsuarioEntity usuarioEntity);

    // Gera a lista de sessões de estudo (o ciclo) para um determinado plano.
    List<SessaoEstudoDTO> gerarCicloDeEstudos(UUID planejamentoId);
    List<SessaoEstudoDTO> buscarCicloEstudo(UUID planejamentoId);

    PlanejamentoDTO arquivarPlanoDeEstudos(PlanejamentoDTO planejamentoParaArquivar);
    PlanejamentoDTO desarquivarPlanoDeEstudos(PlanejamentoDTO planejamentoParaArquivar);
    void remove(UUID id);

    //Seleciona um plano que já foi pré-cadastrado por um ADMIN
    PlanejamentoDTO selecionarPlanoPredefinido(UUID idPlanejamento);

    //Método para verificar se o plano já foi concluído
    boolean verificarSePlanoEstaConcluido(UUID idPlanejamento);

    PlanejamentoDTO refazerPlanejamento(UUID idPlanejamento);
}
