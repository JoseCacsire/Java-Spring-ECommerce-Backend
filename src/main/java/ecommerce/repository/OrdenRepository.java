package ecommerce.repository;

import ecommerce.dto.orden.OrdenResponseDTO;
import ecommerce.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenRepository extends JpaRepository<Orden,Long> {


    List<Orden> findByEstado(String estado);
}