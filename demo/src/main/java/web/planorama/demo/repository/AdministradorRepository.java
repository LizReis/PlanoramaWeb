package web.planorama.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.planorama.demo.entity.UsuarioEntity;

import java.util.UUID;

public interface AdministradorRepository extends JpaRepository<UsuarioEntity, UUID> {

}
