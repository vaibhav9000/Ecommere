package inventory.app.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(String resource, UUID id) {
        super(resource + " not found with id: " + id, HttpStatus.NOT_FOUND, "DATA_NOT_FOUND");
    }
}
