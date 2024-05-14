package ecommerce.dto.orden;

import ecommerce.dto.detail.OrdenDetailRequestDTO;
import ecommerce.dto.detail.OrdenDetailResponseDTO;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrdenRequestDTO(


        @NotNull
        Long usuarioId,

        List<OrdenDetailRequestDTO> ordenDetailRequestDTOList

)
{
}
