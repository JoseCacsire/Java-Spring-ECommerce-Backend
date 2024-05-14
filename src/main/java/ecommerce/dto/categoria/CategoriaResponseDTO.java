package ecommerce.dto.categoria;

import ecommerce.model.Categoria;

public record CategoriaResponseDTO(
    Long id,
    String nombre
) {
    public CategoriaResponseDTO(Categoria entitySaved) {
        this(entitySaved.getId(), entitySaved.getNombre());
    }
}
