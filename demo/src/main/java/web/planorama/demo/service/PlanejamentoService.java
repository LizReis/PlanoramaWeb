package web.planorama.demo.service;

import java.util.List;
import java.util.UUID;

import web.planorama.demo.dto.SessaoEstudoDTO;
import web.planorama.demo.dto.UsuarioDTO;
import web.planorama.demo.dto.PlanejamentoDTO;

public interface PlanejamentoService {
    PlanejamentoDTO save(PlanejamentoDTO planejamentoDTO);
    PlanejamentoDTO findOne(UUID id);
    List<PlanejamentoDTO> findAll(); //Aqui encontra todos os planejamentos existentes
    List<PlanejamentoDTO> findAllOfAdm(); //Aqui encontra todos os planejamento criados por Administadores
    List<PlanejamentoDTO> findAllOfEstudante(UsuarioDTO usuarioDTO); //Aqui encontra todos os planejamentos de um determinado Estudante

    // Gera a lista de sessões de estudo (o ciclo) para um determinado plano.
    List<SessaoEstudoDTO> gerarCicloDeEstudos(UUID planejamentoId);
    List<SessaoEstudoDTO> buscarCicloEstudo(UUID planejamentoId);

    //DEVE CRIAR DEPOIS UM MÉTODO PARA PEGAR TODAS AS MATERIASPLANO DE UM DETERMINADO PLANO

    PlanejamentoDTO atualizarPlanoDeEstudos(UUID id, PlanejamentoDTO planejamentoDTO);
    PlanejamentoDTO arquivarPlanoDeEstudos(UUID id);
    PlanejamentoDTO desArquivarPlanoDeEstudos(UUID id);
    PlanejamentoDTO restaurarPlanoDeEstudos(UUID id);
    void remove(UUID id);

    //Seleciona um plano que já foi pré-cadastrado por um ADMIN
    PlanejamentoDTO selecionarPlanoPredefinido(UUID idPlanejamento);
}
