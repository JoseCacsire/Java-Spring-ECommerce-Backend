package ecommerce.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record CreateUserDTO(
        @NotBlank
        String username,
        @Email
        @NotBlank
        String email,
        String password
) {
}
