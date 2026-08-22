package inventory.app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class CreateInventoryRequest {
    private UUID id;
    private UUID warehouseId;
    private UUID productId;
    private Integer quantity;
    private Integer reservedQuantity;
}