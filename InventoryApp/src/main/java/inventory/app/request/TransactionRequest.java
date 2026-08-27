package inventory.app.request;

import inventory.app.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    private UUID inventoryId;
    private UUID orderId;
    private UUID userId;
    private int quantityChange;
    private TransactionType type;
    private String notes;
}
