package inventory.app.controller;

import inventory.app.enums.CreateInventoryRequest;
import inventory.app.model.Inventory;
import inventory.app.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/inventory")
@AllArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public List<Inventory> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @GetMapping("/{id}")
    public Inventory getInventory(@PathVariable UUID id) {
        return inventoryService.getInventory(id);
    }

    @PostMapping
    public Inventory createInventory(@RequestBody CreateInventoryRequest inventory) {
        return inventoryService.createInventory(inventory);
    }
}
