package ecommerce.controller;

import ecommerce.dto.categoria.CategoriaRequestDTO;
import ecommerce.dto.user.AuthLoginRequest;
import ecommerce.dto.user.AuthResponse;
import ecommerce.dto.user.CreateUserDTO;
import ecommerce.service.UserEntityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")

public class UsuarioController {

    @Autowired
    private UserEntityService userEntityService;

    @PostMapping("/register")
    public ResponseEntity<CreateUserDTO> createUser(@RequestBody CreateUserDTO createUserDTO) {
        CreateUserDTO createdUser = userEntityService.createUser(createUserDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest){
        return new ResponseEntity<>(userEntityService.loginUser(userRequest), HttpStatus.OK);
    }

}
