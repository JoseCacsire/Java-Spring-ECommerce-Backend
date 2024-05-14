package ecommerce.dto.detail;

import ecommerce.model.Orden;
import ecommerce.model.OrdenDetail;
import jakarta.validation.constraints.NotNull;

public record OrdenDetailResponseDTO(
        Long id,
        Integer cantidad,
        Double precio,
        Double subtotal,
        String producto,

        String imagen,
        Long ordenId

) {
    public OrdenDetailResponseDTO(OrdenDetail ordenDetail) {
        this(ordenDetail.getId(), ordenDetail.getCantidad(), ordenDetail.getPrecio(),
                ordenDetail.getSubtotal(),ordenDetail.getProducto().getNombre(),
                ordenDetail.getProducto().getImagen(),ordenDetail.getOrden().getId());
    }


}
