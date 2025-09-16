package web.planorama.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;



import web.planorama.demo.entity.RegistrarEstudoEntity;

public interface RegistrarEstudoRepository extends JpaRepository<RegistrarEstudoEntity, UUID>{

}
