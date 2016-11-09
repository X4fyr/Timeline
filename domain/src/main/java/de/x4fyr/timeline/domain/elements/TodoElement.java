package de.x4fyr.timeline.domain.elements;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Duration;
import java.time.LocalDate;

/**
 * Element stashed as a To-Do with optional date and duration.
 *
 * @author Benedikt Volkmer
 *         Created on 11/4/16.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TodoElement extends Element {

    private LocalDate plannedDate;
    private Duration plannedDuration;

    public TodoElement(String title, String notes) {
        super(title, notes);
    }

}
