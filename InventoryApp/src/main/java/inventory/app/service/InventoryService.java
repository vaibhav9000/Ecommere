package inventory.app.service;

import inventory.app.enums.CreateInventoryRequest;
import inventory.app.model.Inventory;
import inventory.app.model.Product;
import inventory.app.model.Warehouse;
import inventory.app.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final WarehouseService warehouseService;
    private final ProductService productService;

    public List<Inventory> getAllInventory(List<UUID> warehouseIds) {
        if (warehouseIds == null || warehouseIds.isEmpty()) {
            return inventoryRepository.findAllWithProductAndWarehouse();
        }
        List<Inventory> inventoryList = inventoryRepository.findAllByWarehouseIds(warehouseIds);
        return inventoryList;
    }

    public Inventory getInventory(UUID id) {
        return inventoryRepository.findByIdWithProductAndWarehouse(id).orElse(null);
    }

    public Inventory createInventory(CreateInventoryRequest inventory) {
        Warehouse warehouse = warehouseService.getWarehouse(inventory.getWarehouseId());
        Product product = productService.getProduct(inventory.getProductId());
        Inventory newInventory = new Inventory();
        newInventory.setWarehouse(warehouse);
        newInventory.setProduct(product);
        newInventory.setQuantity(inventory.getQuantity());
        newInventory.setReservedQuantity(inventory.getReservedQuantity());
        return inventoryRepository.save(newInventory);
    }

    public Inventory updateInventory(UUID id, CreateInventoryRequest inventory) {
        Inventory existingInventory = inventoryRepository.findById(id).orElse(null);
        if (existingInventory == null) {
            return null;
        }

        if (inventory.getQuantity() != null) {
            existingInventory.setQuantity(inventory.getQuantity());
        }

        if (inventory.getReservedQuantity() != null) {
            existingInventory.setReservedQuantity(inventory.getReservedQuantity());
        }

        return inventoryRepository.save(existingInventory);
    }

}
