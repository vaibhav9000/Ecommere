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

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Inventory getInventory(UUID id) {
        return inventoryRepository.findById(id).orElse(null);
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

}
