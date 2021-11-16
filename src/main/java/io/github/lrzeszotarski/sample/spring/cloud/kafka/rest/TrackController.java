package io.github.lrzeszotarski.sample.spring.cloud.kafka.rest;

import io.github.lrzeszotarski.sample.spring.cloud.kafka.rest.dto.Track;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

@RestController
public class TrackController {

    private final StreamBridge streamBridge;

    public TrackController(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @PostMapping("/tracks")
    public Track freeTrack(@RequestBody Track freeTrack) {
        final Message<Track> trackMessage = MessageBuilder
                .withPayload(freeTrack)
                .setHeader("partitionKey", UUID.randomUUID().toString())
                .setHeader("kafka_messageKey", Objects.requireNonNull(UUID.randomUUID().toString()))
                .build();
        streamBridge.send("tracks.free", trackMessage);
        return freeTrack;
    }
}
