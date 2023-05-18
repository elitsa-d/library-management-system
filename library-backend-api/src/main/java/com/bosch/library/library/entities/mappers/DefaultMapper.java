package com.bosch.library.library.entities.mappers;

import java.util.List;

public interface DefaultMapper<D, E> {

    D toDTO(E entity);

    E toEntity(D DTO);

    List<D> toDTOList(List<E> entities);

    List<E> toEntitiesList(List<D> DTOs);
}
