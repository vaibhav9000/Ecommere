package inventory.app.controller;

import inventory.app.model.Warehouse;
import inventory.app.service.WarehouseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/v1/warehouses")
@AllArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping
    public Warehouse addWarehouse(@RequestBody Warehouse warehouse) {
        return warehouseService.addWarehouse(warehouse);
    }

    @GetMapping
    public List<Warehouse> getAllWarehouses() {
        return warehouseService.getAllWarehouses();
    }

    @GetMapping("/nearest")
    public Warehouse getNearestWarehouse(@RequestParam BigDecimal latitude, @RequestParam BigDecimal longitude) {
        return warehouseService.getNearestWarehouse(latitude, longitude);
    }
}
