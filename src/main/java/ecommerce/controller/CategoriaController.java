package ecommerce.controller;

import ecommerce.dto.categoria.CategoriaRequestDTO;
import ecommerce.dto.categoria.CategoriaResponseDTO;
import ecommerce.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping()
    public ResponseEntity<Iterable<CategoriaResponseDTO>> findAll() {
        try{
            Iterable<CategoriaResponseDTO> categorias = categoriaService.findAll();
            return ResponseEntity.ok(categorias);
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> findById(Long id) {
        try{
            CategoriaResponseDTO categoria = categoriaService.findById(id);
            return ResponseEntity.ok(categoria);
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping()
    public ResponseEntity<?> save(@RequestBody CategoriaRequestDTO categoriaRequestDTO) {
        try{
            CategoriaResponseDTO categoriaResponseDTO = categoriaService.save(categoriaRequestDTO);
            return ResponseEntity.ok(categoriaResponseDTO);
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> update(@PathVariable Long id, @RequestBody CategoriaRequestDTO categoriaRequestDTO) {
        try{
            CategoriaResponseDTO categoriaResponseDTO = categoriaService.update(id, categoriaRequestDTO);
            return ResponseEntity.ok(categoriaResponseDTO);
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try{
            categoriaService.delete(id);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
