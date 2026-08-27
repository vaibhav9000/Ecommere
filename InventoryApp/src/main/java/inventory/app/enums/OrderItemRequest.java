package inventory.app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class OrderItemRequest {
    private UUID inventoryId;
    private UUID userId;
    private int quantity;
}
