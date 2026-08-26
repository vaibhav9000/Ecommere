package inventory.app.controller;

import com.turkraft.springfilter.boot.Filter;
import inventory.app.enums.CreateInventoryRequest;
import inventory.app.model.Inventory;
import inventory.app.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/inventory")
@AllArgsConstructor
@PreAuthorize("hasRole('USER') or hasRole('SERVICE_USER')")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public Page<Inventory> getAll(
            @RequestParam(value = "warehouseIds", required = false) List<UUID> warehouseIds,
            @Filter Specification<Inventory> filter,
            @PageableDefault(size = 10, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return inventoryService.getALlInventory(filter, pageable, warehouseIds);
    }

    @GetMapping("/{id}")
    public Inventory getInventory(@PathVariable UUID id) {
        return inventoryService.getInventory(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('SERVICE_USER')")
    public Inventory createInventory(@RequestBody CreateInventoryRequest inventory) {
        return inventoryService.createInventory(inventory);
    }

    @PatchMapping("/{id}")
    public Inventory updateInventory(@PathVariable UUID id, @RequestBody CreateInventoryRequest inventory) {
        return inventoryService.updateInventory(id, inventory);
    }
}
