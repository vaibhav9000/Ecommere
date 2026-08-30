package inventory.app.controller;

import com.turkraft.springfilter.boot.Filter;
import inventory.app.request.OrderRequest;
import inventory.app.model.Order;
import inventory.app.service.OrderService;
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

//    @GetMapping
//    public Page<Order> getOrders(@Filter Specification<Order> filter,
//            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
//
//    }

    @GetMapping
    public List<Order> getOrders() {
        return orderService.getOrders();
    }
}
