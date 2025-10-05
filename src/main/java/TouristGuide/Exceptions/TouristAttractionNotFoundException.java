package TouristGuide.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TouristAttractionNotFoundException extends RuntimeException {
    public TouristAttractionNotFoundException(String message) {
        super(message);
    }
}
