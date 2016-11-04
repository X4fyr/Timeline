package de.x4fyr.timeline.domain;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author Benedikt Volkmer
 *         Created on 10/30/16.
 */
@Data
@RequiredArgsConstructor
public abstract class Element {

    @NonNull
    private String title;
    @NonNull
    private String notes;
}
