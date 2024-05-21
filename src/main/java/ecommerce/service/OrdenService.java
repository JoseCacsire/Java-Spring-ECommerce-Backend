package ecommerce.service;

import ecommerce.dto.orden.OrdenRequestDTO;
import ecommerce.dto.orden.OrdenResponseDTO;

import java.util.List;

public interface OrdenService {

    List<OrdenResponseDTO> findAll();
    OrdenResponseDTO findById(Long id);
    OrdenResponseDTO createOrden(OrdenRequestDTO ordenRequestDTODTO);

    OrdenResponseDTO realizarOrden(Long id);
//    OrdenResponseDTO updateDetail(Long orderId, Long detailId, OrdenDetailUpdateRequest updatedRequest);
    void delete(Long id);

    List<OrdenResponseDTO> findByEstado(String estado);

}
