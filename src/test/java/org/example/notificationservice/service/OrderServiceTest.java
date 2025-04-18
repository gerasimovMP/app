package org.example.notificationservice.service;

import org.example.notificationservice.exception.OrderNotFoundException;
import org.example.notificationservice.mapper.OrdersMapper;
import org.example.notificationservice.model.OrderDTO;
import org.example.notificationservice.model.OrderEntity;
import org.example.notificationservice.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrdersMapper orderMapper;

    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("order created succes")
    void createOrder_ShouldReturnCreatedOrder() {
        OrderDTO inputDto = new OrderDTO(1L,
                "item-123",
                2,
                LocalDateTime.parse("2023-01-01T10:00:00"),
                "COMPLETED"
        );
        OrderEntity entity = new OrderEntity(1L,
                "item-123",
                2,
                LocalDateTime.parse("2023-01-01T10:00:00"),
                "COMPLETED",
                0L
        );
        OrderDTO expectedDto = new OrderDTO(1L,
                "item-123",
                2,
                LocalDateTime.parse("2023-01-01T10:00:00"),
                "COMPLETED"
        );

        when(orderMapper.toEntity(inputDto)).thenReturn(entity);
        when(orderRepository.save(entity)).thenReturn(entity);
        when(orderMapper.toDto(entity)).thenReturn(expectedDto);

        OrderDTO result = orderService.createOrder(inputDto);

        assertNotNull(result);
        assertEquals(expectedDto, result);
        verify(orderMapper).toEntity(inputDto);
        verify(orderRepository).save(entity);
        verify(orderMapper).toDto(entity);
    }

    @Test
    @DisplayName("order exists")
    void getOrderById_WhenOrderExists_ShouldReturnOrder() {
        Long orderId = 1L;
        OrderEntity entity = new OrderEntity(1L,
                "item-123",
                2,
                LocalDateTime.parse("2023-01-01T10:00:00"),
                "COMPLETED",
                0L
        );
        OrderDTO expectedDto = new OrderDTO(1L,
                "item-123",
                2,
                LocalDateTime.parse("2023-01-01T10:00:00"),
                "COMPLETED"
        );

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(entity));
        when(orderMapper.toDto(entity)).thenReturn(expectedDto);

        OrderDTO result = orderService.getOrderById(orderId);

        assertNotNull(result);
        assertEquals(expectedDto, result);
        verify(orderRepository).findById(orderId);
        verify(orderMapper).toDto(entity);
    }

    @Test
    @DisplayName("order dont exists")
    void getOrderById_WhenOrderNotExists_ShouldThrowException() {

        Long orderId = 1L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(orderId));
        verify(orderRepository).findById(orderId);
        verify(orderMapper, never()).toDto(any());
    }

    @Test
    @DisplayName("all ordres exits")
    void getAllOrders_ShouldReturnListOfOrders() {
        OrderEntity entity = new OrderEntity(1L,
                "item-123",
                2,
                LocalDateTime.parse("2023-01-01T10:00:00"),
                "COMPLETED",
                0L
        );
        List<OrderEntity> entities = Collections.singletonList(entity);
        OrderDTO dto = new OrderDTO(
                1L,
                "item-123",
                2,
                LocalDateTime.parse("2023-01-01T10:00:00"),
                "COMPLETED"
        );
        List<OrderDTO> expectedList = Collections.singletonList(dto);

        when(orderRepository.findAll()).thenReturn(entities);
        when(orderMapper.toDtoList(entities)).thenReturn(expectedList);

        List<OrderDTO> result = orderService.getAllOrders();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedList, result);
        verify(orderRepository).findAll();
        verify(orderMapper).toDtoList(entities);
    }

    @Test
    @DisplayName("order deleted success")
    void deleteOrder_WhenOrderExists_ShouldReturnTrue() {
        Long orderId = 1L;
        OrderEntity entity = new OrderEntity(1L,
                "item-123",
                2,
                LocalDateTime.parse("2023-01-01T10:00:00"),
                "COMPLETED",
                0L
        );

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(entity));

        boolean result = orderService.deleteOrder(orderId);

        assertTrue(result);
        verify(orderRepository).findById(orderId);
        verify(orderRepository).delete(entity);
    }

}