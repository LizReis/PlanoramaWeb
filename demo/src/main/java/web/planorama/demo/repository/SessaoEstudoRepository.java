package web.planorama.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import web.planorama.demo.entity.MateriaPlanejamentoEntity;
import web.planorama.demo.entity.PlanejamentoEntity;
import web.planorama.demo.entity.SessaoEstudoEntity;

public interface SessaoEstudoRepository extends JpaRepository<SessaoEstudoEntity, UUID>{
    List<SessaoEstudoEntity> findByMateriaPlanejamento(MateriaPlanejamentoEntity materiaPlanejamento);

    List<SessaoEstudoEntity> findByPlanejamentoEntity(PlanejamentoEntity planejamentoEntity);
}
