package inventory.app.response;

import inventory.app.enums.OrderStatus;
import inventory.app.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private UUID id;
    private UUID userId;
    private OrderStatus orderStatus;
    private String shippingAddress;
    private double totalAmount;
    private Instant createdAt;
    private Instant updatedAt;
    private List<OrderItem> items;
}
