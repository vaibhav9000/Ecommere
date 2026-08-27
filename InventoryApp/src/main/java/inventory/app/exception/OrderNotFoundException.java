package inventory.app.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class OrderNotFoundException extends BaseException {

    public OrderNotFoundException(UUID id) {
        super("Order not found for id: " + id.toString(), HttpStatus.NOT_FOUND, "ORDER_NOT_FOUND");
    }
}
