package inventory.app.service;

import inventory.app.exception.ResourceNotFoundException;
import inventory.app.model.Warehouse;
import inventory.app.repository.WarehouseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public Warehouse addWarehouse(Warehouse warehouse) {
        log.debug("Adding warehouse: {}", warehouse);
        return warehouseRepository.save(warehouse);
    }

    public List<Warehouse> getAllWarehouses() {
        log.info("Fetching all warehouses");
        return warehouseRepository.findAll();
    }

    public Warehouse getWarehouse(UUID id) {
        log.debug("Fetching warehouse with id: {}", id);
        return warehouseRepository.findById(id)
            .map(warehouse -> {
                log.debug("Warehouse found with id: {}", id);
                return warehouse;
            })
            .orElseThrow(() -> {
                log.warn("Warehouse not found with id: {}", id);
                return new ResourceNotFoundException("Warehouse", id);
            });
    }

    public Warehouse getNearestWarehouse(BigDecimal latitude, BigDecimal longitude) {
        log.debug("Fetching nearest warehouses for latitude: {}, longitude: {}", latitude, longitude);
        List<Warehouse> warehouses = warehouseRepository.findAll();
        Warehouse nearestWarehouse = null;
        double minDistance = 50;

        for (Warehouse warehouse : warehouses) {
            double distance = calculateDistance(latitude.doubleValue(), longitude.doubleValue(), warehouse.getLatitude().doubleValue(), warehouse.getLongitude().doubleValue());
            if (distance < minDistance) {
                minDistance = distance;
                nearestWarehouse = warehouse;
            }
        }
        if (nearestWarehouse == null) {
            log.warn("Nearest warehouse withing distance: {} not found", minDistance);
            throw new ResourceNotFoundException("Warehouse", null);
        }
        log.debug("Nearest warehouse found");
        return nearestWarehouse;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the Earth in kilometers
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
