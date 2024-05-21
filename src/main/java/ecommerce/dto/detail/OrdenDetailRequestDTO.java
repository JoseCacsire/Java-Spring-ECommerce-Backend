package ecommerce.dto.detail;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrdenDetailRequestDTO(
        @NotNull
        @Min(1)
        Integer cantidad,
        @NotNull Long productoId
) {

}
