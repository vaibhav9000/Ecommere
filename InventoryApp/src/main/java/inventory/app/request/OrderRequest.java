package inventory.app.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class OrderRequest {
    private UUID userId;
    private String shippingAddress;
    private List<OrderItemRequest> items;
}
