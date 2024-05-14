package ecommerce.dto.producto;

import ecommerce.model.Producto;

public record ProductoResponseDTO(

    Long id,
    String nombre,
    String descripcion,
    Double precio,
    Double precioCompra,
    Integer stock,
    String imagen,
    String categoria

)
{
    public ProductoResponseDTO(Producto entitySaved) {
        this(entitySaved.getId(), entitySaved.getNombre(), entitySaved.getDescripcion(), entitySaved.getPrecio(),
                entitySaved.getPrecioCompra(), entitySaved.getStock(), entitySaved.getImagen(),
                entitySaved.getCategoria().getNombre());
    }

}
