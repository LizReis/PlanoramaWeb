package web.planorama.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import web.planorama.demo.entity.SessaoEstudoEntity;

public interface SessaoEstudoRepository extends JpaRepository<SessaoEstudoEntity, UUID>{

}
