package ecommerce.controller;


import ecommerce.dto.orden.OrdenRequestDTO;
import ecommerce.dto.orden.OrdenResponseDTO;
import ecommerce.model.OrdenDetail;
import ecommerce.service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    // Endpoint para obtener todas las órdenes
    @GetMapping
    public ResponseEntity<List<OrdenResponseDTO>> getAllOrdenes() {
        List<OrdenResponseDTO> ordenes = ordenService.findAll();
        return ResponseEntity.ok(ordenes);
    }

    // Endpoint para obtener una orden por su ID
    @GetMapping("/{id}")
    public ResponseEntity<OrdenResponseDTO> getOrdenById(@PathVariable Long id) {
        OrdenResponseDTO orden = ordenService.findById(id);
        return ResponseEntity.ok(orden);
    }

    // Endpoint para crear una nueva orden
    @PostMapping
    public ResponseEntity<OrdenResponseDTO> createOrden(@RequestBody OrdenRequestDTO ordenRequestDTO) {
        OrdenResponseDTO nuevaOrden = ordenService.save(ordenRequestDTO);
        return new ResponseEntity<>(nuevaOrden, HttpStatus.CREATED);
    }

    // Endpoint para actualizar un detalle de una orden
    @PutMapping("/{orderId}/detalles/{detailId}")
    public ResponseEntity<OrdenResponseDTO> updateOrdenDetail(
            @PathVariable Long orderId,
            @PathVariable Long detailId,
            @RequestBody OrdenDetail updatedDetail
    ) {
        OrdenResponseDTO ordenActualizada = ordenService.updateDetail(orderId, detailId, updatedDetail);
        return ResponseEntity.ok(ordenActualizada);
    }

}
