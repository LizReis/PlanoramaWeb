package web.planorama.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import web.planorama.demo.entity.PlanejamentoEntity;

import java.util.UUID;

public interface PlanejamentoRepository extends JpaRepository<PlanejamentoEntity, UUID>{
}
