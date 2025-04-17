package org.example.notificationservice.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
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
        Optional<OrderEntity> orderEntity = orderRepository.findById(id);
        if (orderEntity.isPresent()) {
            OrderDTO orderDTO = orderMapper.toDto(orderEntity.get());
            log.info("Order with ID {} found: {}", id, orderDTO);
            return orderDTO;
        }
        log.warn("Order with ID {} not found", id);
        return null;
    }

    public List<OrderDTO> getAllOrders() {
        List<OrderEntity> orderEntities = orderRepository.findAll();
        List<OrderDTO> orders = orderMapper.toDtoList(orderEntities);
        log.info("Fetched {} orders", orders.size());
        return orders;
    }

    @Transactional
    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        Optional<OrderEntity> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            OrderEntity orderEntity = existingOrder.get();
            orderMapper.updateEntityFromDto(orderDTO, orderEntity);
            orderEntity = orderRepository.save(orderEntity);
            OrderDTO updatedOrder = orderMapper.toDto(orderEntity);
            log.info("Order with ID {} updated successfully", id);
            return updatedOrder;
        }
        log.warn("Order with ID {} not found for update", id);
        return null;
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
    @Transactional
    public OrderDTO createOrderSyncViaSoap(OrderDTO orderDTO) {
        OrderEntity orderEntity = orderMapper.toEntity(orderDTO);
        orderEntity = orderRepository.save(orderEntity);
        OrderDTO createdOrder = orderMapper.toDto(orderEntity);
        log.info("Order created successfully with ID: {}", createdOrder.getId());
        return createdOrder;
    }

}
