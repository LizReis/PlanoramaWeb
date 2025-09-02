package web.planorama.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.planorama.demo.entity.PlanejamentoEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlanejamentoRepository extends JpaRepository<PlanejamentoEntity, UUID> {

    // Busca no banco de dados todos os planejamentos onde o campo 'preDefinidoAdm' é true.
    List<PlanejamentoEntity> findByPreDefinidoAdmIsTrue();

    // Busca no banco de dados todos os planejamentos de um usuário específico.

    List<PlanejamentoEntity> findByCriador_Id(UUID criadorId);
}