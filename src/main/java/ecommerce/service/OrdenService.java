package ecommerce.service;

import ecommerce.dto.orden.OrdenRequestDTO;
import ecommerce.dto.orden.OrdenResponseDTO;
import ecommerce.dto.producto.ProductoRequestDTO;
import ecommerce.dto.producto.ProductoResponseDTO;
import ecommerce.model.OrdenDetail;
import ecommerce.model.Producto;

import java.util.List;

public interface OrdenService {

    List<OrdenResponseDTO> findAll();
    OrdenResponseDTO findById(Long id);
    OrdenResponseDTO save(OrdenRequestDTO ordenRequestDTODTO);
    OrdenResponseDTO updateDetail(Long orderId, Long detailId, OrdenDetail updatedDetail);
    void delete(Long id);


}
