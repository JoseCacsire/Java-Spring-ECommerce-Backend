package ecommerce.service.impl;

import ecommerce.dto.producto.ProductoRequestDTO;
import ecommerce.dto.producto.ProductoResponseDTO;
import ecommerce.model.Categoria;
import ecommerce.model.Producto;
import ecommerce.repository.ProductoRepository;
import ecommerce.service.CategoriaService;
import ecommerce.service.ProductoService;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaServiceImpl categoriaService;

    @Override
    public List<ProductoResponseDTO> findAll() {
        return productoRepository.findAll()
                .stream()
                .map(ProductoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoResponseDTO findById(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto con id " + id + " no encontrado"));
        return new ProductoResponseDTO(producto);
    }

    @Override
    public ProductoResponseDTO save(ProductoRequestDTO productoRequestDTO) {
        Categoria categoria = categoriaService.findById2(productoRequestDTO.categoriaId());
        try {
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
        } catch (Exception e) {
            throw new ServiceException("Error occurred while saving Producto", e);
        }
    }



    @Override
    public ProductoResponseDTO update(Long id, ProductoRequestDTO productoRequestDTO) {
        Categoria categoria = categoriaService.findById2(productoRequestDTO.categoriaId());
        try {
            Producto producto = Producto.builder()
                    .id(id)
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
        } catch (Exception e) {
            throw new ServiceException("Error occurred while saving Producto", e);
        }
    }

    @Override
    public void delete(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public Producto findById2(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no reservation with that id in the database"));
    }
}
