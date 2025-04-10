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

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }


    public OrderDTO createOrder(OrderDTO orderDTO) {
        OrderEntity orderEntity = orderMapper.toEntity(orderDTO);
        orderEntity = orderRepository.save(orderEntity);
        return orderMapper.toDto(orderEntity);
    }

    public OrderDTO getOrderById(Long id) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(id);
        return orderEntity.map(entity -> orderMapper.toDto(entity)).orElse(null);
    }


    public List<OrderDTO> getAllOrders() {
        List<OrderEntity> orderEntities = orderRepository.findAll();
        return orderMapper.toDtoList(orderEntities);
    }


    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        Optional<OrderEntity> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            OrderEntity orderEntity = existingOrder.get();


            orderMapper.updateEntityFromDto(orderDTO, orderEntity);

            orderEntity = orderRepository.save(orderEntity);

            return orderMapper.toDto(orderEntity);
        }
        return null;
    }

    public boolean deleteOrder(Long id) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(id);
        if (orderEntity.isPresent()) {
            orderRepository.delete(orderEntity.get());
            return true;
        }
        return false;
    }

}
