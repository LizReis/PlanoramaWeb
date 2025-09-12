package web.planorama.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import web.planorama.demo.entity.PlanejamentoEntity;

import java.util.UUID;
import java.util.List;
import web.planorama.demo.entity.UsuarioEntity;


public interface PlanejamentoRepository extends JpaRepository<PlanejamentoEntity, UUID>{

    List<PlanejamentoEntity> findAllByCriador(UsuarioEntity criador);
}
