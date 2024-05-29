package ecommerce.dto.orden;

import ecommerce.dto.detail.OrdenDetailRequestDTO;
import ecommerce.dto.detail.OrdenDetailResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrdenRequestDTO(


        @NotNull
        Long usuarioId,

        @Valid //Para que el manejo de validaciones también afecte a la lista de OrdenDetailRequestDTO dentro de OrdenRequestDTO
        List<OrdenDetailRequestDTO> ordenDetailRequestDTOList

)
{
}
