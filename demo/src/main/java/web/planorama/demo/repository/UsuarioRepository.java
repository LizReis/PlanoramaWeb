package web.planorama.demo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import web.planorama.demo.entity.UsuarioEntity;
import java.util.List;


public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID>{

    //JPA cria a consulta automaticamente pelo nome do método
    Optional<UsuarioEntity> findByEmail(String email);

    Optional<UsuarioEntity> findById(UUID id);

    boolean existsByEmail(String email);
}
