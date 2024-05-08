package io.github.guimatech.hexagonal.application.repositories;

import io.github.guimatech.hexagonal.application.domain.event.Event;
import io.github.guimatech.hexagonal.application.domain.event.EventId;

import java.util.Optional;

public interface EventRepository {

    Optional<Event> eventOfId(EventId anId);

    Event create(Event event);

    Event update(Event event);

    void deleteAll();
}
