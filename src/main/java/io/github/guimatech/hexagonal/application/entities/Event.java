package io.github.guimatech.hexagonal.application.entities;

import io.github.guimatech.hexagonal.application.exceptions.ValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Event {

    private final EventId eventId;
    private Name name;
    private LocalDate date;
    private int totalSpots;
    private PartnerId partnerId;

    public Event(final EventId eventId, final String name, final String date, final Integer totalSpots, final PartnerId partnerId) {
        if (Objects.isNull(eventId)) {
            throw new ValidationException("Invalid value for EventId");
        }

        if (Objects.isNull(date)) {
            throw new ValidationException("Invalid value for date");
        }

        if (Objects.isNull(totalSpots)) {
            throw new ValidationException("Invalid value for totalSpots");
        }

        if (Objects.isNull(partnerId)) {
            throw new ValidationException("Invalid value for partnerId");
        }

        this.eventId = eventId;
        this.name = new Name(name);
        this.date = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        this.totalSpots = totalSpots;
        this.partnerId = partnerId;
    }

    public static Event newEvent(final String name, final String date, final Integer totalSpots, final Partner partner) {
        return new Event(EventId.unique(), name, date, totalSpots, partner.partnerId());
    }

    public EventId eventId() {
        return eventId;
    }

    public Name name() {
        return name;
    }

    public LocalDate date() {
        return date;
    }

    public int totalSpots() {
        return totalSpots;
    }

    public PartnerId partnerId() {
        return partnerId;
    }
}
