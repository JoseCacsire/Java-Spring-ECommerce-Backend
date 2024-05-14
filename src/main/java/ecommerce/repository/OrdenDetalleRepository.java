package ecommerce.repository;

import ecommerce.model.OrdenDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenDetalleRepository extends JpaRepository<OrdenDetail,Long> {

}
