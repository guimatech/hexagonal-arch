package io.github.guimatech.hexagonal.application.repositories;

import io.github.guimatech.hexagonal.application.entities.Event;
import io.github.guimatech.hexagonal.application.entities.EventId;

import java.util.Optional;

public interface EventRepository {

    Optional<Event> eventOfId(EventId anId);

    Event create(Event event);

    Event update(Event event);
}
