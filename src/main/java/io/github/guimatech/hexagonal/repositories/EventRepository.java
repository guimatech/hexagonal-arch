package io.github.guimatech.hexagonal.repositories;

import io.github.guimatech.hexagonal.models.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {

}
