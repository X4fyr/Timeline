package de.x4fyr.timeline.services;

import de.x4fyr.timeline.domain.ScheduledElement;
import de.x4fyr.timeline.domain.Timeline;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Service interface serving the use-cases on a timeline.
 *
 * @param <E> Type of elements in the timeline
 * @author Benedikt Volkmer
 *         Created on 11/4/16.
 */
public interface TimelineService<E extends ScheduledElement> {

    Timeline<E> getToday();

    Timeline<E> getDate(LocalDate date);

    Timeline<E> getTimeSpan(LocalDateTime start, LocalDateTime end);

}
