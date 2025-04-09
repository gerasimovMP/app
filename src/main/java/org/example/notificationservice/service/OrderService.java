package org.example.notificationservice.service;

import org.example.notificationservice.mapper.OrderMapper;
import org.example.notificationservice.model.OrderDTO;
import org.example.notificationservice.model.OrderEntity;
import org.example.notificationservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public void processOrder(OrderDTO dto) {
        OrderEntity entity = orderMapper.toEntity(dto);
        orderRepository.save(entity);
    }
}
/*    // Получить все заказы
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    // Получить заказ по ID
    public Optional<OrderEntity> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // Создать новый заказ
    public OrderEntity createOrder(OrderEntity orderEntity) {
        OrderEntity savedOrder = orderRepository.save(orderEntity);

        return savedOrder;
    }

    // Обновить существующий заказ
    public OrderEntity updateOrder(OrderEntity orderEntity) {
        if (!orderRepository.existsById(orderEntity.getId())) {
            throw new RuntimeException("Order not found");
        }

        return orderRepository.save(orderEntity);
    }

    // Удалить заказ
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found");
        }
        orderRepository.deleteById(id);
    }*//*

}
*/
