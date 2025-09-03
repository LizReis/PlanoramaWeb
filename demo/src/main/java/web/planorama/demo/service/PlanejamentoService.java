package web.planorama.demo.service;

import java.util.List;
import java.util.UUID;

import web.planorama.demo.dto.EstudanteDTO;
import web.planorama.demo.dto.PlanejamentoDTO;

public interface PlanejamentoService {
    PlanejamentoDTO save(PlanejamentoDTO planejamentoDTO);
    PlanejamentoDTO findOne(UUID id);
    List<PlanejamentoDTO> findAll(); //Aqui encontra todos os planejamentos existentes
    List<PlanejamentoDTO> findAllOfAdm(); //Aqui encontra todos os planejamento criados por Administadores
    List<PlanejamentoDTO> findAllOfEstudante(EstudanteDTO estudanteDTO); //Aqui encontra todos os planejamentos de um determinado Estudante
    //DEVE CRIAR DEPOIS UM MÉTODO PARA PEGAR TODAS AS MATERIASPLANO DE UM DETERMINADO PLANO

    PlanejamentoDTO atualizarPlanoDeEstudos(UUID id, PlanejamentoDTO planejamentoDTO);
    PlanejamentoDTO arquivarPlanoDeEstudos(UUID id);
    PlanejamentoDTO desArquivarPlanoDeEstudos(UUID id);
    PlanejamentoDTO restaurarPlanoDeEstudos(UUID id);
    void remove(UUID id);
}
