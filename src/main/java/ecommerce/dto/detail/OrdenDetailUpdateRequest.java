package ecommerce.dto.detail;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrdenDetailUpdateRequest(
        @NotNull
        @Min(1)
        Integer cantidad
) {

}
