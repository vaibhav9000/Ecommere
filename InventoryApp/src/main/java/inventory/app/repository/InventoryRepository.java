package inventory.app.repository;

import com.turkraft.springfilter.boot.Filter;
import inventory.app.model.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
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
}
