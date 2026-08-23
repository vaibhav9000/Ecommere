package inventory.app.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class InsufficientStockException extends BaseException {

    public InsufficientStockException(String productName, UUID id) {
        super("Insufficient stock for: " + productName + ", inventoryId: " + id + ".", HttpStatus.CONFLICT, "INSUFFICIENT_STOCK");
    }
}
