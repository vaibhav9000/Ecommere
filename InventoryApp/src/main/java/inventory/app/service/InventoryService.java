package inventory.app.service;

import inventory.app.enums.TransactionType;
import inventory.app.request.InventoryRequest;
import inventory.app.exception.ResourceNotFoundException;
import inventory.app.model.Inventory;
import inventory.app.model.Product;
import inventory.app.model.Warehouse;
import inventory.app.repository.InventoryRepository;
import inventory.app.request.TransactionRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private final TransactionService transactionService;

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

    @Transactional
    public Inventory createInventory(InventoryRequest inventory) {
        Warehouse warehouse = warehouseService.getWarehouse(inventory.getWarehouseId());
        Product product = productService.getProduct(inventory.getProductId());
        Inventory newInventory = new Inventory();
        newInventory.setWarehouse(warehouse);
        newInventory.setProduct(product);
        newInventory.setQuantity(inventory.getQuantity());
        newInventory.setReservedQuantity(inventory.getReservedQuantity());
        newInventory = inventoryRepository.save(newInventory);
        inventoryRepository.flush();
        TransactionRequest transaction = new TransactionRequest(newInventory.getId(), null, null, inventory.getQuantity(), TransactionType.ADD, "");
        transactionService.addTransaction(transaction);
        return newInventory;
    }

    @Transactional
    public Inventory updateInventory(UUID id, InventoryRequest inventory) {
        Inventory existingInventory = inventoryRepository.findByIdForUpdate(id).orElseThrow(() -> new ResourceNotFoundException("Inventory", id));
        int prevQuantity = existingInventory.getQuantity();
        if (inventory.getQuantity() != null) {
            existingInventory.setQuantity(prevQuantity + inventory.getQuantity());
        }

        if (inventory.getReservedQuantity() != null) {
            existingInventory.setReservedQuantity(existingInventory.getReservedQuantity() + inventory.getReservedQuantity());
        }
        TransactionRequest transaction = new TransactionRequest(id, null, null, inventory.getQuantity(), TransactionType.UPDATE, "");
        transactionService.addTransaction(transaction);
        return inventoryRepository.save(existingInventory);
    }

    public int decrementQuantityIfAvailable(UUID id, int qty) {
        return inventoryRepository.decrementQuantityIfAvailable(id, qty);
    }

    public int incrementQuantityIfAvailable(UUID id, int qty) {
        return inventoryRepository.incrementQuantityIfAvailable(id, qty);
    }

}
