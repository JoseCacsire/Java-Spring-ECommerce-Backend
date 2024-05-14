package ecommerce.service.impl;

import ecommerce.dto.categoria.CategoriaRequestDTO;
import ecommerce.dto.categoria.CategoriaResponseDTO;
import ecommerce.model.Categoria;
import ecommerce.repository.CategoriaRepository;
import ecommerce.service.CategoriaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;


    @Override
    public List<CategoriaResponseDTO> findAll() {
        return categoriaRepository.findAll()
                .stream()
                .map(CategoriaResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaResponseDTO findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria con id " + id + " no encontrado"));
        return new CategoriaResponseDTO(categoria);
    }

    @Override
    public Categoria findById2(Long id) {
        return categoriaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No hay reservaciones con ese id en la base de datos"));
    }


    @Override
    public CategoriaResponseDTO save(CategoriaRequestDTO categoriaRequestDTO) {
        try {
            Categoria categoria = Categoria.builder()
                    .nombre(categoriaRequestDTO.nombre())
                    .build();
            Categoria entitySaved = categoriaRepository.save(categoria);
            return new CategoriaResponseDTO(entitySaved);
        } catch (Exception e) {
            throw new ServiceException("Error occurred while saving Categoria", e);
        }
    }

    @Override
    public CategoriaResponseDTO update(Long id, CategoriaRequestDTO categoriaRequestDTO) {
        try {
            Categoria categoria = Categoria.builder()
                    .nombre(categoriaRequestDTO.nombre())
                    .build();
            Categoria entitySaved = categoriaRepository.save(categoria);
            return new CategoriaResponseDTO(entitySaved);
        } catch (Exception e) {
            throw new ServiceException("Categoria con id " + categoriaRequestDTO.nombre() + " no encontrado");
        }
    }

    @Override
    public void delete(Long id) {
        categoriaRepository.deleteById(id);
    }
}
