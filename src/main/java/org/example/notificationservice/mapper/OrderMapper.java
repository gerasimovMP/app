package org.example.notificationservice.mapper;

import org.example.notificationservice.model.OrderDTO;
import org.example.notificationservice.model.OrderEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderDTO toDto(OrderEntity entity) {
        if (entity == null) {
            return null;
        }

        OrderDTO dto = new OrderDTO();
        dto.setId(entity.getId());
        dto.setItemId(entity.getItemId());
        dto.setQuantity(entity.getQuantity());
        dto.setOrderDate(entity.getOrderDate());
        dto.setStatus(entity.getStatus());

        return dto;
    }

    public OrderEntity toEntity(OrderDTO dto) {
        if (dto == null) {
            return null;
        }

        OrderEntity entity = new OrderEntity();
        entity.setId(dto.getId());
        entity.setItemId(dto.getItemId());
        entity.setQuantity(dto.getQuantity());
        entity.setOrderDate(dto.getOrderDate());
        entity.setStatus(dto.getStatus());

        return entity;
    }

    public List<OrderDTO> toDtoList(List<OrderEntity> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDto(OrderDTO dto, OrderEntity entity) {
        if (dto == null || entity == null) {
            return;
        }
        if (dto.getItemId() != null) {
            entity.setItemId(dto.getItemId());
        }
        if (dto.getQuantity() != 0) {
            entity.setQuantity(dto.getQuantity());
        }
        if (dto.getOrderDate() != null) {
            entity.setOrderDate(dto.getOrderDate());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
    }
}