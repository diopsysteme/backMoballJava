package org.example.backmobile.Web.Dtos.Mapper;
import org.mapstruct.Mapper;
public interface GenericMapper<D, E> {
    D toDto(E entity);
    E toEntity(D dto);
}