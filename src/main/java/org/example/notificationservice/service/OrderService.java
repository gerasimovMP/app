package org.example.notificationservice.service;

import org.example.notificationservice.mapper.OrderMapper;
import org.example.notificationservice.model.OrderDTO;
import org.example.notificationservice.model.OrderEntity;
import org.example.notificationservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;  // Добавляем маппер

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    // Синхронная обработка заказа
    public OrderDTO createOrder(OrderDTO orderDTO) {
        // Преобразуем OrderDTO в OrderEntity с помощью маппера
        OrderEntity orderEntity = orderMapper.toEntity(orderDTO);

        // Сохраняем заказ в базе данных
        orderEntity = orderRepository.save(orderEntity);

        // Преобразуем OrderEntity обратно в OrderDTO
        return orderMapper.toDto(orderEntity);
    }

/*    // Асинхронная обработка заказа
    @Async
    public void createOrderAsync(OrderDTO orderDTO) {
        // Преобразуем OrderDTO в OrderEntity с помощью маппера
        OrderEntity orderEntity = orderMapper.toEntity(orderDTO);

        // Сохраняем заказ в базе данных (асинхронно)
        orderRepository.save(orderEntity);

        // Дополнительная логика по отправке уведомлений или обработке
        System.out.println("Asynchronously created order: " + orderEntity);
    }*/
}
