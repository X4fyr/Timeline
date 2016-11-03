package de.x4fyr.timeline.domain;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author Benedikt Volkmer
 *         Created on 10/30/16.
 */
@Data
@RequiredArgsConstructor
public class Element {

    @NonNull
    private LocalDateTime start;
    @NonNull
    private Duration duration;
    @NonNull
    private String title;
    @NonNull
    private String notes;
}
