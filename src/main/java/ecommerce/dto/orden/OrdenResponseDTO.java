package ecommerce.dto.orden;

import ecommerce.dto.detail.OrdenDetailResponseDTO;
import ecommerce.model.Orden;

import java.util.List;

public record OrdenResponseDTO(
        Long ordenId,
        Double total,
        String usuario,

        String estado,

        List<OrdenDetailResponseDTO> ordenDetailResponseDTOList
) {

    public OrdenResponseDTO(Orden orden) {
        this(orden.getId(), orden.getTotal(),
                orden.getUserEntity().getUsername(),orden.getEstado(),
                orden.getOrdenDetailList().stream().map(OrdenDetailResponseDTO::new).toList());
    }
}
