package io.github.lrzeszotarski.sample.spring.cloud.kafka.rest;

import io.github.lrzeszotarski.sample.spring.cloud.kafka.rest.dto.Track;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrackController {

    private final StreamBridge streamBridge;

    public TrackController(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @PostMapping("/tracks")
    public Track freeTrack(@RequestBody Track freeTrack) {
        streamBridge.send("tracks.free", freeTrack);
        return freeTrack;
    }
}
