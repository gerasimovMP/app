package org.example.notificationservice.controller;

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
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrderSync(@RequestBody OrderDTO orderDTO) {
        System.out.println("Received POST request on /api/orders");
        try {
            OrderDTO createdOrder = orderService.createOrder(orderDTO);
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrderSync(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        System.out.println("Received PUT request on /api/orders from first app");
        try {
            OrderDTO updatedOrder = orderService.updateOrder(id, orderDTO);
            if (updatedOrder != null) {
                return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        System.out.println("Received GET request on /api/orders");
        try {
            List<OrderDTO> orders = orderService.getAllOrders();
            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        System.out.println("Received single GET request on /api/orders");
        try {
            OrderDTO order = orderService.getOrderById(id);
            if (order != null) {
                return new ResponseEntity<>(order, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        System.out.println("Received DELETE request on /api/orders");
        try {
            boolean isDeleted = orderService.deleteOrder(id);
            if (isDeleted) {
                return new ResponseEntity<>("Order deleted successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error occurred while deleting the order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
