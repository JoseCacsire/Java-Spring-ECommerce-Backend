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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public CategoriaResponseDTO save(CategoriaRequestDTO categoriaRequestDTO) {
            Categoria categoria = Categoria.builder()
                    .nombre(categoriaRequestDTO.nombre())
                    .build();
            Categoria entitySaved = categoriaRepository.save(categoria);
            return new CategoriaResponseDTO(entitySaved);
    }

    @Override
    @Transactional
    public CategoriaResponseDTO update(Long id, CategoriaRequestDTO categoriaRequestDTO) {
            Categoria categoria = categoriaRepository.findById(id)
                    .orElseThrow(()->new EntityNotFoundException("Categoria con id "+id+" no encontrado"));
            categoria.setNombre(categoriaRequestDTO.nombre());
            Categoria entitySaved = categoriaRepository.save(categoria);
            return new CategoriaResponseDTO(entitySaved);
    }

    @Override
    public void delete(Long id) {
        categoriaRepository.deleteById(id);
    }
}
