package inventory.app.service;

import inventory.app.enums.*;
import inventory.app.request.OrderItemRequest;
import inventory.app.request.OrderRequest;
import inventory.app.request.TransactionRequest;
import inventory.app.exception.InsufficientStockException;
import inventory.app.exception.OrderNotFoundException;
import inventory.app.model.Inventory;
import inventory.app.model.Order;
import inventory.app.model.OrderItem;
import inventory.app.repository.OrderItemRepository;
import inventory.app.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final InventoryService inventoryService;
    private final TransactionService transactionService;

    @Transactional
    public Order createOrder(OrderRequest request) {
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        order.setUserId(request.getUserId());
        order.setShippingAddress(request.getShippingAddress());
        order = orderRepository.save(order);
        orderRepository.flush();

        UUID orderId = order.getId();
        List<OrderItem> items = request.getItems().stream()
            .map(orderItem -> createOrderItem(orderItem, orderId, request.getUserId())).toList();
        double totalAmount = items.stream().mapToDouble(OrderItem::getTotalPrice).sum();
        order.setTotal_amount(totalAmount);
        return orderRepository.save(order);
    }

    private OrderItem createOrderItem(OrderItemRequest request, UUID orderId, UUID userId) {
        // check for sufficient capacity and update
        int updated = inventoryService.decrementQuantityIfAvailable(request.getInventoryId(), request.getQuantity());
        if (updated == 0) {
            throw new InsufficientStockException("Inventory", request.getInventoryId());
        }
        // add transaction
        TransactionRequest transactionRequest = new TransactionRequest(
                request.getInventoryId(), orderId, userId,
                -request.getQuantity(), TransactionType.BUY, "");
        transactionService.addTransaction(transactionRequest);
        // save item
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderId);
        orderItem.setInventoryId(request.getInventoryId());
        orderItem.setQuantity(request.getQuantity());
        Inventory inventory = inventoryService.getInventory(request.getInventoryId());
        double unitPrice = inventory.getProduct().getPrice();
        orderItem.setUnitPrice(unitPrice);
        orderItem.setTotalPrice(unitPrice * request.getQuantity());
        return orderItemRepository.save(orderItem);
    }

    public Order confirmOrder(UUID id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> {
            log.warn("Order not found for id: {}, can't confirm order", id);
            throw new OrderNotFoundException(id);
        });
        order.setStatus(OrderStatus.CONFIRMED);
        return orderRepository.save(order);
    }

    public Order cancelOrder(UUID id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> {
            log.warn("Order not found for id: {}, can't cancel order", id);
            throw new OrderNotFoundException(id);
        });
        order.setStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

}
