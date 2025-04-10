package org.example.notificationservice.service;

import lombok.extern.slf4j.Slf4j;
import org.example.notificationservice.model.OrderDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerService {

    private final OrderService orderService;

    public ConsumerService(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = "order_topic", groupId = "group_id", containerFactory = "kafkaListenerContainerFactory")
    public void listenOrder(OrderDTO orderDTO) {
        try {
            log.info("Received order: {}", orderDTO);
            orderService.createOrder(orderDTO);
            log.info("Order processed successfully: {}", orderDTO);
        } catch (Exception e) {
            log.error("Error processing order: {}", e.getMessage(), e);
        }
    }
}
