package inventory.app.model;

import inventory.app.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "transactions",
        indexes = {
                @Index(name = "idx_transaction_order_id", columnList = "order_id"),
                @Index(name = "idx_transaction_user_id", columnList = "user_id"),
                @Index(name = "idx_transaction_type", columnList = "type")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID inventoryId;

    private UUID orderId;

    private UUID userId;

    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false)
    private int quantityChange;

    private String notes;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant createdAt;
}
