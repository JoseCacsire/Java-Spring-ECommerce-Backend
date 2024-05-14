package ecommerce.service;

import ecommerce.dto.user.AuthLoginRequest;
import ecommerce.dto.user.AuthResponse;
import ecommerce.dto.user.CreateUserDTO;

public interface UserEntityService {

    CreateUserDTO createUser(CreateUserDTO createUserDTO);

    AuthResponse loginUser(AuthLoginRequest userRequest);
}
