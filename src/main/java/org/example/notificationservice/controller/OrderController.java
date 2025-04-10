package org.example.notificationservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.notificationservice.model.OrderDTO;
import org.example.notificationservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrderSync(@RequestBody OrderDTO orderDTO) {
        log.info("Received POST request on /api/orders with order data: {}", orderDTO);
        try {
            OrderDTO createdOrder = orderService.createOrder(orderDTO);
            log.info("Order created successfully: {}", createdOrder);
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error occurred while creating the order: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrderSync(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        log.info("Received PUT request on /api/orders/{} with order data: {}", id, orderDTO);
        try {
            OrderDTO updatedOrder = orderService.updateOrder(id, orderDTO);
            if (updatedOrder != null) {
                log.info("Order updated successfully: {}", updatedOrder);
                return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
            }
            log.warn("Order with ID {} not found for update", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error occurred while updating the order with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        log.info("Received GET request on /api/orders to fetch all orders");
        try {
            List<OrderDTO> orders = orderService.getAllOrders();
            if (orders.isEmpty()) {
                log.info("No orders found");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while fetching all orders: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        log.info("Received GET request on /api/orders/{} to fetch order", id);
        try {
            OrderDTO order = orderService.getOrderById(id);
            if (order != null) {
                log.info("Fetched order with ID {}: {}", id, order);
                return new ResponseEntity<>(order, HttpStatus.OK);
            }
            log.warn("Order with ID {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error occurred while fetching the order with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        log.info("Received DELETE request on /api/orders/{}", id);
        try {
            boolean isDeleted = orderService.deleteOrder(id);
            if (isDeleted) {
                log.info("Order with ID {} deleted successfully", id);
                return new ResponseEntity<>("Order deleted successfully", HttpStatus.OK);
            }
            log.warn("Order with ID {} not found for deletion", id);
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error occurred while deleting the order with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>("Error occurred while deleting the order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
