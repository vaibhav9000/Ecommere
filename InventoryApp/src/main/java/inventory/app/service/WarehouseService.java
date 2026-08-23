package inventory.app.service;

import inventory.app.exception.ResourceNotFoundException;
import inventory.app.model.Warehouse;
import inventory.app.repository.WarehouseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public Warehouse addWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    public Warehouse getWarehouse(UUID id) {
        return warehouseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Warehouse", id));
    }

    public Warehouse getNearestWarehouse(BigDecimal latitude, BigDecimal longitude) {
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
            throw new ResourceNotFoundException("Warehouse", null);
        }
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
