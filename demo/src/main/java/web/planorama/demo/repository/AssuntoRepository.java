package web.planorama.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import web.planorama.demo.entity.AssuntoEntity;

public interface AssuntoRepository extends JpaRepository<AssuntoEntity, UUID>{

}
