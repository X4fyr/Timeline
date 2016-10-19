package de.x4fyr.timeline.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.threeten.bp.Duration;
import org.threeten.bp.LocalDateTime;

/**
 * Created by x4fyr on 10/16/16.
 */
@Data
@Builder
@RequiredArgsConstructor
public class Element {

    @NonNull private LocalDateTime start;
    @NonNull private Duration duration;
    @NonNull private String title;
    @NonNull private String notes;
}
