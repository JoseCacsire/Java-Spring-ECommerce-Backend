package ecommerce.controller;

import ecommerce.dto.orden.OrdenRequestDTO;
import ecommerce.dto.orden.OrdenResponseDTO;
import ecommerce.service.OrdenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    private final OrdenService ordenService;


    // Endpoint para obtener todas las órdenes
    @GetMapping
    public ResponseEntity<List<OrdenResponseDTO>> getAllOrdenes() {
        List<OrdenResponseDTO> ordenes = ordenService.findAll();
        return ResponseEntity.ok(ordenes);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<OrdenResponseDTO>> getOrdenesByEstado(@PathVariable String estado){
        List<OrdenResponseDTO> ordenesByEstado = ordenService.findByEstado(estado);
        return ResponseEntity.ok(ordenesByEstado);
    }


    // Endpoint para obtener una orden por su ID
    @GetMapping("/{id}")
    public ResponseEntity<OrdenResponseDTO> getOrdenById(@PathVariable Long id) {
        OrdenResponseDTO orden = ordenService.findById(id);
        return ResponseEntity.ok(orden);
    }

    // Endpoint para crear una nueva orden
    @PostMapping("/crear")
    public ResponseEntity<?> createOrden(@RequestBody OrdenRequestDTO ordenRequestDTO) {
        try{
            OrdenResponseDTO nuevaOrden = ordenService.createOrden(ordenRequestDTO);
            return new ResponseEntity<>(nuevaOrden, HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PatchMapping("/realizar/{id}")
    public ResponseEntity<?>  realizarOrden(@PathVariable Long id){
        try {
            OrdenResponseDTO ordenRealizada = ordenService.realizarOrden(id);
            return new ResponseEntity<>(ordenRealizada,HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }



}
