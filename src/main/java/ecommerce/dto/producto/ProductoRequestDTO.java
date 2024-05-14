package ecommerce.dto.producto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductoRequestDTO(
        @NotBlank
        String nombre,
        @NotBlank
        String descripcion,
        @NotNull
        @DecimalMin("0.0")
        Double precio,
        @NotNull
        @DecimalMin("0.0")
        Double precioCompra,
        @NotNull
        @Min(0)
        Integer stock,
        String imagen,
        @NotNull
        Long categoriaId
) {
}
