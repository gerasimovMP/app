package org.example.notificationservice.mapper;


import org.example.notificationservice.model.OrderDTO;
import org.example.notificationservice.model.OrderEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrdersMapper {
    OrderDTO toDto(OrderEntity entity);

    OrderEntity toEntity(OrderDTO dto);

    List<OrderDTO> toDtoList(List<OrderEntity> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(OrderDTO dto, @MappingTarget OrderEntity entity);
}