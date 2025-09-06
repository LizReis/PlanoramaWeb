package web.planorama.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import web.planorama.demo.entity.MateriaPlanejamentoEntity;
import web.planorama.demo.entity.PlanejamentoEntity;

import java.util.List;


public interface MateriaPlanejamentoRepository extends JpaRepository<MateriaPlanejamentoEntity, UUID>{
    List<MateriaPlanejamentoEntity> findByPlanejamentoEntity(PlanejamentoEntity planejamentoEntity);
}
