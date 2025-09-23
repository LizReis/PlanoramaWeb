package web.planorama.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;



import web.planorama.demo.entity.RegistrarEstudoEntity;

public interface RegistrarEstudoRepository extends JpaRepository<RegistrarEstudoEntity, UUID>{

    //FindAll responsável por pegar todos os registros de estudo para de um planejamento entre duas datas
    //Vamos utiliza-lo para ver se a pessoa bateu a meta diária de acordo com o registro do estudo do dia
    List<RegistrarEstudoEntity> findAllByMateriaPlanejamento_PlanejamentoEntity_IdAndDataRegistroBetween(UUID planejamentoId, LocalDateTime  start, LocalDateTime  end);


}
