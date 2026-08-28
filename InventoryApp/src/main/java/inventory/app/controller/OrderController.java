package inventory.app.controller;

import inventory.app.request.OrderRequest;
import inventory.app.model.Order;
import inventory.app.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/orders")
@AllArgsConstructor
@PreAuthorize("hasRole('USER') or hasRole('SERVICE_USER')")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @PutMapping("/{id}/confirm")
    public Order confirmOrder(@PathVariable UUID id) {
        return orderService.confirmOrder(id);
    }

    @PutMapping("/{id}/cancel")
    public Order cancelOrder(@PathVariable UUID id) {
        return orderService.cancelOrder(id);
    }

}
