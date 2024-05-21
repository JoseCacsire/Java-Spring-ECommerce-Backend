package ecommerce.service.impl;

import ecommerce.dto.detail.OrdenDetailRequestDTO;
import ecommerce.dto.detail.OrdenDetailUpdateRequest;
import ecommerce.dto.orden.OrdenRequestDTO;
import ecommerce.dto.orden.OrdenResponseDTO;
import ecommerce.model.*;
import ecommerce.repository.OrdenDetalleRepository;
import ecommerce.repository.OrdenRepository;
import ecommerce.repository.ProductoRepository;
import ecommerce.repository.UserEntityRepository;
import ecommerce.service.OrdenService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class OrdenServiceImpl implements OrdenService {

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private OrdenDetalleRepository ordenDetalleRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Override
    public List<OrdenResponseDTO> findByEstado(String estado) {
        return ordenRepository.findByEstado(estado).stream()
                .map(OrdenResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrdenResponseDTO> findAll() {
        return ordenRepository.findAll().stream()
                .map(OrdenResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public OrdenResponseDTO findById(Long id) {
        Orden orden = ordenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orden con id " + id + " no encontrado"));
        return new OrdenResponseDTO(orden);

    }

    @Override
    public OrdenResponseDTO realizarOrden(Long id) {
        Orden orden = ordenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orden con id " + id + " no encontrado"));

        orden.setEstado("Completado");
        orden.getOrdenDetailList().forEach(this::actualizarStock);

        return new OrdenResponseDTO(ordenRepository.save(orden));

    }


    @Override
    @Transactional
    public OrdenResponseDTO createOrden(OrdenRequestDTO ordenRequestDTO) {
        // Buscar al usuario por su ID
        UserEntity usuario = userEntityRepository.findById(ordenRequestDTO.usuarioId())
                .orElseThrow(() -> new ServiceException("Usuario no encontrado con ID: " + ordenRequestDTO.usuarioId()));

        List<OrdenDetail> ordenDetailList = ordenRequestDTO.ordenDetailRequestDTOList().stream()
                .map(this::createOrdenDetail)
                .collect(Collectors.toList());

        double totalOrden = ordenDetailList.stream()
                .mapToDouble(OrdenDetail::getSubtotal)
                .sum();

        Orden orden = Orden.builder()
                .total(totalOrden)
                .estado("Pendiente")
                .userEntity(usuario)
                .ordenDetailList(ordenDetailList)
                .build();

        Orden ordenSaved = ordenRepository.save(orden);

        ordenDetailList.forEach(ordenDetail -> {
            ordenDetail.setOrden(ordenSaved);
            ordenDetalleRepository.save(ordenDetail);
            //cuando al roden haya sido pagada
//            this.actualizarStock(ordenDetail);
        });

        return new OrdenResponseDTO(ordenSaved);

    }



    private void actualizarStock(OrdenDetail ordenDetail){
        Producto producto = ordenDetail.getProducto();
        int cantidadOrdenada = ordenDetail.getCantidad();
        int stockActual = producto.getStock();

        if (stockActual < cantidadOrdenada){
            throw new ServiceException("No hay suficiente stock disponible para el producto: " + producto.getNombre());
        }
        producto.setStock(stockActual - cantidadOrdenada);
        productoRepository.save(producto);
    }



    private OrdenDetail createOrdenDetail(OrdenDetailRequestDTO detailRequest) {
        Producto producto = productoRepository.findById(detailRequest.productoId())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        validateDetailRequest(detailRequest);

        OrdenDetail ordenDetail = new OrdenDetail();
        ordenDetail.setCantidad(detailRequest.cantidad());
        ordenDetail.setPrecio(producto.getPrecio());
        ordenDetail.setSubtotal(detailRequest.cantidad() * producto.getPrecio());
        ordenDetail.setProducto(producto);

        return ordenDetail;
    }



    private void validateDetailRequest(OrdenDetailRequestDTO detailRequest) {
        if (detailRequest.cantidad() <= 0) {
            throw new ServiceException("Cantidad debe ser valor positivo.");
        }
    }


    @Override
    public void delete(Long id) {

    }


}
