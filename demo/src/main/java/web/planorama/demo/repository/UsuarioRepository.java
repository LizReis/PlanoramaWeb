package web.planorama.demo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import web.planorama.demo.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID>{

    //JPA cria a consulta automaticamente pelo nome do método
    Optional<UsuarioEntity> findByEmail(String email);
}
