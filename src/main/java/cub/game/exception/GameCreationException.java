package cub.game.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GameCreationException extends RuntimeException{

    public GameCreationException(String message) {
        super(message);
    }
}
