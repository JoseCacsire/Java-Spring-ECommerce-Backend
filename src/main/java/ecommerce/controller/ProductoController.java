package ecommerce.controller;

import ecommerce.dto.producto.ProductoRequestDTO;
import ecommerce.dto.producto.ProductoResponseDTO;
import ecommerce.service.CategoriaService;
import ecommerce.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    @GetMapping()
    public ResponseEntity<Iterable<ProductoResponseDTO>> findAll() {
        try{
            Iterable<ProductoResponseDTO> productos = productoService.findAll();
            return ResponseEntity.ok(productos);
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> findById(Long id) {
        try{
            ProductoResponseDTO producto = productoService.findById(id);
            return ResponseEntity.ok(producto);
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping()
    public ResponseEntity<?> save(@RequestBody ProductoRequestDTO productoRequestDTO) {
        try{
            ProductoResponseDTO productoResponseDTO = productoService.save(productoRequestDTO);
            return ResponseEntity.ok(productoResponseDTO);
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> update(@PathVariable Long id, @RequestBody ProductoRequestDTO productoRequestDTO) {
        try{
            ProductoResponseDTO productoResponseDTO = productoService.update(id, productoRequestDTO);
            return ResponseEntity.ok(productoResponseDTO);
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try{
            productoService.delete(id);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
