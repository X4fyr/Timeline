package de.x4fyr.timeline.domain.elements;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Element coming from an external source like a calendar.
 *
 * @author Benedikt Volkmer
 *         Created on 11/4/16.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ExternalElement extends ScheduledElement {

    @NonNull
    private UUID externalUuid;
    private boolean scheduled = false;


    public ExternalElement(@NonNull UUID uuid, LocalDateTime start, Duration duration, String
            title, String notes) {
        super(start, duration, title, notes);
        this.externalUuid = uuid;
    }

}
