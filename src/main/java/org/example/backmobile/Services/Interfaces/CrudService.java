package org.example.backmobile.Services.Interfaces;

import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface CrudService<Entity,dto,dtoUpdate> {
    Entity Create(dto dto);
    Entity Update(Long id,dtoUpdate dto);
    Optional<Entity> Delete(Long id);
    List<Entity> findAll();
    Optional<Entity> findById(Long id);
}
