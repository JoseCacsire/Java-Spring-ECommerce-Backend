package ecommerce.dto.categoria;

import jakarta.validation.constraints.NotBlank;

public record CategoriaRequestDTO(
        @NotBlank
        String nombre
) {
}
