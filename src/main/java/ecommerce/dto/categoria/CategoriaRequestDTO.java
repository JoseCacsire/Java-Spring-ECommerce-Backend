package ecommerce.dto.categoria;

import jakarta.validation.constraints.NotNull;

public record CategoriaRequestDTO(
        @NotNull
        String nombre
) {
}
