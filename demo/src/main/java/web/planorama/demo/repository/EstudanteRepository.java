package web.planorama.demo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import web.planorama.demo.entity.EstudanteEntity;

public interface EstudanteRepository extends JpaRepository<EstudanteEntity, UUID>{

    Optional <EstudanteEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
