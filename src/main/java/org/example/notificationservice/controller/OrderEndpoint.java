package org.example.notificationservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.notificationservice.CreateOrderRequest;
import org.example.notificationservice.CreateOrderResponse;
import org.example.notificationservice.model.OrderDTO;
import org.example.notificationservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Slf4j
@Endpoint
public class OrderEndpoint {

    private static final String NAMESPACE_URI = "http://notificationservice.example.org";
    private final OrderService orderService;

    @Autowired
    public OrderEndpoint(OrderService orderService) {
        this.orderService = orderService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createOrderRequest")
    @ResponsePayload
    public CreateOrderResponse createOrder(@RequestPayload CreateOrderRequest request) {
        log.info("Received createOrderRequest: id={}, itemId={}, quantity={}, orderDate={}, status={}",
                request.getId(), request.getItemId(), request.getQuantity(), request.getOrderDate(), request.getStatus());
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(request.getId());
        orderDTO.setItemId(request.getItemId());
        orderDTO.setQuantity(request.getQuantity());
        if (request.getOrderDate() != null) {
            orderDTO.setOrderDate(request.getOrderDate().toGregorianCalendar().toZonedDateTime().toLocalDateTime());
        }
        orderDTO.setStatus(request.getStatus());
        log.info("Mapped to OrderDTO: {}", orderDTO);
        OrderDTO savedOrder = orderService.createOrder(orderDTO);
        log.info("Order saved: id={}, itemId={}, quantity={}, date={}, status={}",
                savedOrder.getId(), savedOrder.getItemId(), savedOrder.getQuantity(), savedOrder.getOrderDate(), savedOrder.getStatus());

        CreateOrderResponse response = new CreateOrderResponse();
        response.setStatus("SUCCESS");
        log.info("Returning response: status={}", response.getStatus());
        return response;
    }
}