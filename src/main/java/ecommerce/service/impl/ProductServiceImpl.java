package ecommerce.service.impl;

import ecommerce.dto.producto.ProductoRequestDTO;
import ecommerce.dto.producto.ProductoResponseDTO;
import ecommerce.exceptions.BussinesException;
import ecommerce.model.Categoria;
import ecommerce.model.Producto;
import ecommerce.repository.CategoriaRepository;
import ecommerce.repository.ProductoRepository;
import ecommerce.service.CategoriaService;
import ecommerce.service.ProductoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    private final CategoriaRepository categoriaRepository;

    @Override
    public List<ProductoResponseDTO> findAll() {
        List<ProductoResponseDTO> products = productoRepository.findAll()
                .stream()
                .map(ProductoResponseDTO::new)
                .toList();
        if (products.isEmpty()){
            throw new BussinesException("No hay registros de productos en el sistema", HttpStatus.NOT_FOUND);
        }
        return products;
    }

    @Override
    public ProductoResponseDTO findById(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto con id " + id + " no encontrado"));
        return new ProductoResponseDTO(producto);
    }

    @Override
    @Transactional
    public ProductoResponseDTO save(ProductoRequestDTO productoRequestDTO) {
        Categoria categoria = categoriaRepository.findById(productoRequestDTO.categoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria con id " + productoRequestDTO.categoriaId() + " no encontrado"));
//      Pasando los valores para despues guardar
        Producto producto = Producto.builder()
                .nombre(productoRequestDTO.nombre())
                .descripcion(productoRequestDTO.descripcion())
                .precio(productoRequestDTO.precio())
                .precioCompra(productoRequestDTO.precioCompra())
                .stock(productoRequestDTO.stock())
                .imagen(productoRequestDTO.imagen())
                .categoria(categoria)
                .build();
        Producto entitySaved = productoRepository.save(producto);
        return new ProductoResponseDTO(entitySaved);
    }



    @Override
    @Transactional
    public ProductoResponseDTO update(Long id, ProductoRequestDTO productoRequestDTO) {
        Categoria categoria = categoriaRepository.findById(productoRequestDTO.categoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria con id " + id + " no encontrado"));
        // Buscar el producto existente por su ID
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto con id " + id + " no encontrado"));

        // Actualizar los campos del producto existente
        producto.setNombre(productoRequestDTO.nombre());
        producto.setDescripcion(productoRequestDTO.descripcion());
        producto.setPrecio(productoRequestDTO.precio());
        producto.setPrecioCompra(productoRequestDTO.precioCompra());
        producto.setStock(productoRequestDTO.stock());
        producto.setImagen(productoRequestDTO.imagen());
        producto.setCategoria(categoria);

        Producto entitySaved = productoRepository.save(producto);

        return new ProductoResponseDTO(entitySaved);
    }

    @Override
    public void delete(Long id) {
        productoRepository.deleteById(id);
    }

}
