package web.planorama.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import web.planorama.demo.entity.MateriaEntity;

public interface MateriaRepository extends JpaRepository<MateriaEntity, UUID>{

}
