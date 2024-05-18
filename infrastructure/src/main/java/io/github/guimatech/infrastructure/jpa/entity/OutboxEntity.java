package io.github.guimatech.infrastructure.jpa.entity;

import io.github.guimatech.domain.DomainEvent;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

@Entity(name = "Outbox")
@Table(name = "outbox")
public class OutboxEntity {

    @Id
    private UUID id;

    @Column(columnDefinition = "JSON", length = 4_000)
    private String content;

    private boolean published;

    public OutboxEntity() {
    }

    public OutboxEntity(UUID id, String content, boolean published) {
        this.id = id;
        this.content = content;
        this.published = published;
    }

    public static OutboxEntity of(final DomainEvent domainEvent, final Function<DomainEvent, String> toJson) {
        return new OutboxEntity(
                UUID.fromString(domainEvent.domainEventId()),
                toJson.apply(domainEvent),
                false
        );
    }

    public UUID id() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String content() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean published() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OutboxEntity that = (OutboxEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public OutboxEntity notePublished() {
        this.published = true;
        return this;
    }
}