package ecommerce.exceptions;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BussinesException extends RuntimeException{

    private HttpStatus status;

    public BussinesException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
