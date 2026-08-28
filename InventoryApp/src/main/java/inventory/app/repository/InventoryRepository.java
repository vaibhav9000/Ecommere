package inventory.app.repository;

import com.turkraft.springfilter.boot.Filter;
import inventory.app.model.Inventory;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, UUID>, JpaSpecificationExecutor<Inventory> {
    @Query("select i from Inventory i join fetch i.product p join fetch i.warehouse w")
    Page<Inventory> findAllWithProductAndWarehouse(Specification<Inventory> filter, Pageable pageable);

    @Query("select i from Inventory i join fetch i.product p join fetch i.warehouse w where w.id in :warehouseIds")
    List<Inventory> findAllByWarehouseIds(List<UUID> warehouseIds);

    @Query("select i from Inventory i join fetch i.product p join fetch i.warehouse w where i.id = :id")
    Optional<Inventory> findByIdWithProductAndWarehouse(UUID id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select i from Inventory i where i.id = :id")
    Optional<Inventory> findByIdForUpdate(UUID id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Inventory i set i.quantity = i.quantity - :qty where i.id = :id and i.quantity >= :qty")
    int decrementQuantityIfAvailable(@Param("id") UUID id, @Param("qty") int qty);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Inventory i set i.quantity = i.quantity + :qty where i.id = :id")
    int incrementQuantityIfAvailable(@Param("id") UUID id, @Param("qty") int qty);
}
