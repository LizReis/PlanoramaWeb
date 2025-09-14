package web.planorama.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import web.planorama.demo.entity.AssuntoEntity;
import java.util.List;
import web.planorama.demo.entity.MateriaEntity;


public interface AssuntoRepository extends JpaRepository<AssuntoEntity, UUID>{

    List<AssuntoEntity> findAllByMateriaEntity(MateriaEntity materiaEntity);

}
