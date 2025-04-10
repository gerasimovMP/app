package org.example.notificationservice.controller;

import org.example.notificationservice.model.OrderDTO;
import org.example.notificationservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Синхронный эндпоинт для создания заказа
    @PostMapping
    public ResponseEntity<OrderDTO> createOrderSync(@RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.createOrder(orderDTO);
        return new ResponseEntity<>(createdOrder, HttpStatus.OK);
    }


/*    // Асинхронный эндпоинт для создания заказа
    @PostMapping("/async")
    public ResponseEntity<String> createOrderAsync(@RequestBody OrderDTO orderDTO) {
        orderService.createOrderAsync(orderDTO);
        return new ResponseEntity<>("Order is being processed asynchronously", HttpStatus.ACCEPTED);
    }*/
}
