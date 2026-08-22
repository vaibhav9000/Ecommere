package inventory.app.repository;

import inventory.app.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
    @Query("select i from Inventory i join fetch i.product p join fetch i.warehouse w")
    List<Inventory> findAllWithProductAndWarehouse();

    @Query("select i from Inventory i join fetch i.product p join fetch i.warehouse w where w.id in :warehouseIds")
    List<Inventory> findAllByWarehouseIds(List<UUID> warehouseIds);

    @Query("select i from Inventory i join fetch i.product p join fetch i.warehouse w where i.id = :id")
    Optional<Inventory> findByIdWithProductAndWarehouse(UUID id);
}
