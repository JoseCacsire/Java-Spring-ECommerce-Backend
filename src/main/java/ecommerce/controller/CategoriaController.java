package ecommerce.controller;

import ecommerce.dto.categoria.CategoriaRequestDTO;
import ecommerce.dto.categoria.CategoriaResponseDTO;
import ecommerce.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping()
    public ResponseEntity<Iterable<CategoriaResponseDTO>> findAll() {
            Iterable<CategoriaResponseDTO> categorias = categoriaService.findAll();
            return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> findById(@PathVariable Long id) {
            CategoriaResponseDTO categoria = categoriaService.findById(id);
            return ResponseEntity.ok(categoria);
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> save(@RequestBody @Valid CategoriaRequestDTO categoriaRequestDTO) {
            CategoriaResponseDTO categoriaResponseDTO = categoriaService.save(categoriaRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(categoriaResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> update(@PathVariable Long id, @RequestBody CategoriaRequestDTO categoriaRequestDTO) {
            CategoriaResponseDTO categoriaResponseDTO = categoriaService.update(id, categoriaRequestDTO);
            return ResponseEntity.ok(categoriaResponseDTO);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> delete(@PathVariable Long id) {
//        try{
//            categoriaService.delete(id);
//            return ResponseEntity.ok().build();
//        } catch (ServiceException e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }

}
