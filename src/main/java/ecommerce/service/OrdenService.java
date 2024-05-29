package ecommerce.service;

import ecommerce.dto.orden.OrdenRequestDTO;
import ecommerce.dto.orden.OrdenResponseDTO;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrdenService {

    List<OrdenResponseDTO> findAll();
    OrdenResponseDTO findById(Long id);
    OrdenResponseDTO createOrden(OrdenRequestDTO ordenRequestDTODTO);

    OrdenResponseDTO realizarOrden(Long id);
    void delete(Long id);
    @Query("SELECT o FROM Orden o WHERE o.estado LIKE %:estado%") //funciona con mysql no h2,ignora (tildes,mayusculas e minusculas)
    List<OrdenResponseDTO> findByEstado(String estado);

}
