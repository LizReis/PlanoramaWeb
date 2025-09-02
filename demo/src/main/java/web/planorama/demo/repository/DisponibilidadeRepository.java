package web.planorama.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.planorama.demo.entity.DisponibilidadeEntity;

import java.util.UUID;

@Repository
public interface DisponibilidadeRepository extends JpaRepository<DisponibilidadeEntity, UUID> {
}