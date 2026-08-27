package inventory.app.service;

import com.turkraft.springfilter.boot.Filter;
import inventory.app.enums.InventoryRequest;
import inventory.app.exception.ResourceNotFoundException;
import inventory.app.model.Inventory;
import inventory.app.model.Product;
import inventory.app.model.Warehouse;
import inventory.app.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class InventoryService{

//    protected Set<String> allowedFitlerFields() {
//        return Set.of("id", "quantity", "reservedQuantity");
//    }
//
//    protected Set<String> allowedSortFields() {
//        return Set.of("quantity", "reservedQuantity", "updatedAt");
//    }

    private final InventoryRepository inventoryRepository;
    private final WarehouseService warehouseService;
    private final ProductService productService;

    public Page<Inventory> getALlInventory(Specification<Inventory> filter, Pageable pageable, List<UUID> warehouseIds) {
//        if (warehouseIds == null || warehouseIds.isEmpty()) {
//            return inventoryRepository.findAllWithProductAndWarehouse(filter, pageable);
//        }
//        List<Inventory> inventoryList = inventoryRepository.findAllByWarehouseIds(warehouseIds);
//        return inventoryList;
        return inventoryRepository.findAll(pageable);
    }

    public Inventory getInventory(UUID id) {
        return inventoryRepository.findByIdWithProductAndWarehouse(id).orElseThrow(() -> new ResourceNotFoundException("Inventory", id));
    }

    public Inventory createInventory(InventoryRequest inventory) {
        Warehouse warehouse = warehouseService.getWarehouse(inventory.getWarehouseId());
        Product product = productService.getProduct(inventory.getProductId());
        Inventory newInventory = new Inventory();
        newInventory.setWarehouse(warehouse);
        newInventory.setProduct(product);
        newInventory.setQuantity(inventory.getQuantity());
        newInventory.setReservedQuantity(inventory.getReservedQuantity());
        return inventoryRepository.save(newInventory);
    }

    public Inventory updateInventory(UUID id, InventoryRequest inventory) {
        Inventory existingInventory = inventoryRepository.findById(id).orElse(null);
        if (existingInventory == null) {
            throw new ResourceNotFoundException("Inventory", id);
        }

        if (inventory.getQuantity() != null) {
            existingInventory.setQuantity(inventory.getQuantity());
        }

        if (inventory.getReservedQuantity() != null) {
            existingInventory.setReservedQuantity(inventory.getReservedQuantity());
        }

        return inventoryRepository.save(existingInventory);
    }

    public int decrementQuantityIfAvailable(UUID id, int qty) {
        return inventoryRepository.decrementQuantityIfAvailable(id, qty);
    }

}
