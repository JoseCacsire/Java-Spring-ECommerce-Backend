package ecommerce.service;

import ecommerce.dto.categoria.CategoriaRequestDTO;
import ecommerce.dto.categoria.CategoriaResponseDTO;
import ecommerce.model.Categoria;

import java.util.List;

public interface CategoriaService {

    List<CategoriaResponseDTO> findAll();
    CategoriaResponseDTO findById(Long id);
    CategoriaResponseDTO save(CategoriaRequestDTO categoriaRequestDTO);
    CategoriaResponseDTO update(Long id, CategoriaRequestDTO categoriaRequestDTO);
    void delete(Long id);

    Categoria findById2(Long id);

}
