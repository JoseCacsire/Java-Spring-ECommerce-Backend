package ecommerce.service.impl;

import ecommerce.dto.detail.OrdenDetailRequestDTO;
import ecommerce.dto.orden.OrdenRequestDTO;
import ecommerce.dto.orden.OrdenResponseDTO;
import ecommerce.model.*;
import ecommerce.repository.OrdenDetalleRepository;
import ecommerce.repository.OrdenRepository;
import ecommerce.repository.ProductoRepository;
import ecommerce.repository.UserEntityRepository;
import ecommerce.service.OrdenService;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public OrdenResponseDTO save(OrdenRequestDTO ordenRequestDTO) {

        try {
            // Buscar al usuario por su ID
            UserEntity usuario = userEntityRepository.findById(ordenRequestDTO.usuarioId())
                    .orElseThrow(() -> new ServiceException("Usuario no encontrado con ID: " + ordenRequestDTO.usuarioId()));

            Orden orden = new Orden();
            List<OrdenDetail> ordenDetailList = new ArrayList<>();
            Double totalOrden = 0.0;
            for (OrdenDetailRequestDTO detailRequest : ordenRequestDTO.ordenDetailRequestDTOList()) {
                Producto producto = productoRepository.findById(detailRequest.productoId())
                        .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

                // Validar que la cantidad y el precio sean positivos
                if (detailRequest.cantidad() <= 0 || detailRequest.precio() <= 0) {
                    throw new ServiceException("Cantidad y precio deben ser valores positivos.");
                }

                OrdenDetail ordenDetail = new OrdenDetail();
                ordenDetail.setCantidad(detailRequest.cantidad());
                ordenDetail.setPrecio(detailRequest.precio());
                ordenDetail.setSubtotal(detailRequest.cantidad() * detailRequest.precio());
                ordenDetail.setProducto(producto);
//                ordenDetail.setOrden(orden);

                ordenDetailList.add(ordenDetail);

                // Actualizamos el total de la orden sumando el subtotal de este detalle
                totalOrden += ordenDetail.getSubtotal();
            }

            orden.setTotal(totalOrden);// Asignamos el total a la orden
            orden.setUserEntity(usuario);
            orden.setOrdenDetailList(ordenDetailList);

            Orden ordenSaved = ordenRepository.save(orden);

            // Ahora que la orden principal está guardada, asignarla a los detalles y guardarlos
            for (OrdenDetail ordenDetail : ordenDetailList) {
                ordenDetail.setOrden(ordenSaved);
                ordenDetalleRepository.save(ordenDetail);
            }

            return new OrdenResponseDTO(ordenSaved);
        } catch (Exception e) {
            throw new ServiceException("Error occurred while saving Orden", e);
        }
    }

    @Override
    public OrdenResponseDTO updateDetail(Long orderId, Long detailId, OrdenDetail updatedDetail) {

        try {
            // Buscar la orden por su ID
            Orden orden = ordenRepository.findById(orderId)
                    .orElseThrow(() -> new EntityNotFoundException("Orden con id " + orderId + " no encontrado"));

            // Verificar si el detalle que se quiere actualizar pertenece a esta orden
            OrdenDetail ordenDetailToUpdate = orden.getOrdenDetailList().stream()
                    .filter(detail -> detail.getId().equals(detailId))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("Detalle de orden con id " + detailId + " no encontrado en la orden " + orderId));

            // Actualizar los campos del detalle
            ordenDetailToUpdate.setCantidad(updatedDetail.getCantidad());
            ordenDetailToUpdate.setPrecio(updatedDetail.getPrecio());
            ordenDetailToUpdate.setSubtotal(updatedDetail.getCantidad() * updatedDetail.getPrecio());

            // Actualizar el total de la orden
            double totalOrden = orden.getOrdenDetailList().stream()
                    .mapToDouble(det -> det.getCantidad() * det.getPrecio())
                    .sum();
            orden.setTotal(totalOrden);

            // Guardar la orden actualizada
            Orden ordenUpdated = ordenRepository.save(orden);
            return new OrdenResponseDTO(ordenUpdated);
        } catch (Exception e) {
            throw new ServiceException("Error occurred while updating Orden detail", e);
        }

    }


    @Override
    public void delete(Long id) {

    }
}
