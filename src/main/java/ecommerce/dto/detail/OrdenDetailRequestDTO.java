package ecommerce.dto.detail;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrdenDetailRequestDTO(
        @NotBlank
        String nombre,
        @NotNull
        @Min(1)
        Integer cantidad,
        @NotNull
        @DecimalMin("0.0") Double precio,
        @NotNull
        @DecimalMin("0.0") Double subtotal,
        @NotNull Long ordenId,
        @NotNull Long productoId
) {

}
