package inventory.app.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@Slf4j
public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(String resource, UUID id) {
        super(resource + " not found with id: " + id, HttpStatus.NOT_FOUND, "DATA_NOT_FOUND");
        log.error("{} not found with id: {}", resource, id);
    }
}
