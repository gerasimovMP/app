package org.example.notificationservice.service;

import org.example.notificationservice.model.OrderDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    private final OrderService orderService;

    public ConsumerService(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = "order_topic", groupId = "group_id", containerFactory = "kafkaListenerContainerFactory")
    public void listenOrder(OrderDTO orderDTO) {
        try {
            System.out.println("Received order: " + orderDTO);
            orderService.createOrder(orderDTO); // Теперь вызываем синхронный метод для обработки
            System.out.println("Order processed successfully: " + orderDTO);
        } catch (Exception e) {
            System.err.println("Error processing order: " + e.getMessage());
        }
    }
}
