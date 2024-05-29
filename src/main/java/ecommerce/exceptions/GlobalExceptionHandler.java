package ecommerce.exceptions;

import ecommerce.dto.user.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@Slf4j
@RestControllerAdvice // @RestControllerAdvice en Spring: maneja excepciones globales de forma centralizada para controladores RESTful.
public class GlobalExceptionHandler {

    /*Cada método está anotado con @ExceptionHandler y acepta como parámetro la excepción que debe manejar.
      Devolviendo un ResponseEntity personalizado con un mensaje de error y un código de estado HTTP adecuado.*/
    @ExceptionHandler(MethodArgumentNotValidException.class)//para manejar tipos específicos de excepciones.
    public ResponseEntity<?> tratarError400(MethodArgumentNotValidException e){
        log.error("tratarError400");
        var errores = e.getFieldErrors().stream().map(DataErrorValidation::new).toList();
        return ResponseEntity.badRequest().body(errores);

    }

    @ExceptionHandler(BussinesException.class)
    public ResponseEntity<ErrorResponse> businesExceptionHandler(BussinesException exception){
        log.error("BussinesException");
        ErrorResponse errorResponse = ErrorResponse.builder()
                .date(LocalDateTime.now())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, exception.getStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handlerRuntimeException(RuntimeException exception){
        log.error("handleRuntimeException");
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse error = ErrorResponse.builder()
                .date(LocalDateTime.now())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(error, status);
    }


    private record DataErrorValidation(String campo, String error){
        public DataErrorValidation(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

}
