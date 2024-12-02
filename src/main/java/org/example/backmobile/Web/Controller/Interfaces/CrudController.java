package org.example.backmobile.Web.Controller.Interfaces;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface CrudController<Entity,dto,dtoUpdae> {
    ResponseEntity<Entity> Create(dto dto);
    Entity Update(Long id,dtoUpdae dto);
    ResponseEntity<List<Entity>> findAll();
    Optional<Entity> findById(Long id);
}
