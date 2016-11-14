package de.x4fyr.timeline.domain.elements;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Element scheduled for a specific date and time.
 *
 * @author Benedikt Volkmer
 *         Created on 11/4/16.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ScheduledElement extends Element {

    @NonNull
    private LocalDateTime start;
    @NonNull
    private Duration duration;
    private UUID externalUUID;

    /**
     * Constructor.
     *
     * @param start    Start of the element
     * @param duration Duration of the element
     * @param title    title of the element
     * @param notes    notes of the element
     */
    public ScheduledElement(@NonNull LocalDateTime start, @NonNull Duration duration, String title, String notes) {
        super(title, notes);
        this.start = start;
        this.duration = duration;
    }
}
