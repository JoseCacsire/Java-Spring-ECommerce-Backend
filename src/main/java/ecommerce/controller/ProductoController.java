package ecommerce.controller;

import ecommerce.dto.producto.ProductoRequestDTO;
import ecommerce.dto.producto.ProductoResponseDTO;
import ecommerce.service.CategoriaService;
import ecommerce.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping()
    public ResponseEntity<ProductoResponseDTO> save(@RequestBody @Valid ProductoRequestDTO productoRequestDTO) {
            ProductoResponseDTO productoResponseDTO = productoService.save(productoRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(productoResponseDTO);
    }
    @GetMapping()
    public ResponseEntity<Iterable<ProductoResponseDTO>> findAll() {
            Iterable<ProductoResponseDTO> productos = productoService.findAll();
            return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> findById(@PathVariable Long id) {
            ProductoResponseDTO producto = productoService.findById(id);
            return ResponseEntity.ok(producto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductoRequestDTO productoRequestDTO) {
            ProductoResponseDTO productoResponseDTO = productoService.update(id, productoRequestDTO);
            return ResponseEntity.ok(productoResponseDTO);
    }


//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> delete(@PathVariable Long id) {
//        try{
//            productoService.delete(id);
//            return ResponseEntity.ok().build();
//        } catch (ServiceException e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }

}
