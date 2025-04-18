package org.example.notificationservice.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.notificationservice.exception.OrderNotFoundException;
import org.example.notificationservice.mapper.OrdersMapper;
import org.example.notificationservice.model.OrderDTO;
import org.example.notificationservice.model.OrderEntity;
import org.example.notificationservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrdersMapper orderMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrdersMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        OrderEntity orderEntity = orderMapper.toEntity(orderDTO);
        orderEntity = orderRepository.save(orderEntity);
        OrderDTO createdOrder = orderMapper.toDto(orderEntity);
        log.info("Order created successfully with ID: {}", createdOrder.getId());
        return createdOrder;
    }

    public OrderDTO getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(orderEntity -> {
                    log.info("Order with ID {} found", id);
                    return orderMapper.toDto(orderEntity);
                })
                .orElseThrow(() -> {
                    log.warn("Order with ID {} not found", id);
                    return new OrderNotFoundException("Order not found with id: " + id);
                });
    }

    public List<OrderDTO> getAllOrders() {
        List<OrderEntity> orderEntities = orderRepository.findAll();
        List<OrderDTO> orders = orderMapper.toDtoList(orderEntities);
        log.info("Fetched {} orders", orders.size());
        return orders;
    }

    @Transactional
    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        return orderRepository.findById(id)
                .map(existingOrder -> {
                    orderMapper.updateEntityFromDto(orderDTO, existingOrder);
                    OrderEntity updatedOrder = orderRepository.save(existingOrder);
                    log.info("Order with ID {} updated successfully", id);
                    return orderMapper.toDto(updatedOrder);
                })
                .orElseThrow(() -> {
                    log.warn("Order with ID {} not found for update", id);
                    return new OrderNotFoundException("Order not found with id: " + id);
                });
    }

    public boolean deleteOrder(Long id) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(id);
        if (orderEntity.isPresent()) {
            orderRepository.delete(orderEntity.get());
            log.info("Order with ID {} deleted successfully", id);
            return true;
        }
        log.warn("Order with ID {} not found for deletion", id);
        return false;
    }
}
