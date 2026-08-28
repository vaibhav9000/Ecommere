package inventory.app.repository;

import inventory.app.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
    @Query("select i from OrderItem i where i.orderId = :orderId")
    List<OrderItem> findItemsByOrderId(UUID orderId);
}

