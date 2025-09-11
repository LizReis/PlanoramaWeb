package web.planorama.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import web.planorama.demo.entity.PapelEntity;
import java.util.List;


public interface PapelRepository extends JpaRepository<PapelEntity, Long>{
    Optional<PapelEntity> findByNome(String nome);
}
