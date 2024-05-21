package io.github.guimatech.infrastructure.gateways;

public interface QueueGateway {
    void publish(String content);
}
