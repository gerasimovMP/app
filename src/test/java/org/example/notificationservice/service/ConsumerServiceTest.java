package org.example.notificationservice.service;

import org.example.notificationservice.model.OrderDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class ConsumerServiceTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private ConsumerService consumerService;

    @Test
    @DisplayName("order success processed from Kafka")
    void listenOrder_ShouldProcessOrderSuccessfully() {
        OrderDTO testOrder = new OrderDTO(
                1L,
                "item-123",
                2,
                null,
                "COMPLETED"
        );

        consumerService.listenOrder(testOrder);

        verify(orderService, times(1)).createOrder(testOrder);
        verifyNoMoreInteractions(orderService);
    }
}