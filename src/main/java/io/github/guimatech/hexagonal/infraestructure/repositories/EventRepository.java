package io.github.guimatech.hexagonal.infraestructure.repositories;

import io.github.guimatech.hexagonal.infraestructure.models.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {

}
