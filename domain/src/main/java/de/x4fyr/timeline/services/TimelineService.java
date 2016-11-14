package de.x4fyr.timeline.services;

import de.x4fyr.timeline.domain.Timeline;
import de.x4fyr.timeline.domain.elements.ScheduledElement;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Service interface serving the use-cases on a timeline.
 *
 * @param <E> Type of elements in the timeline
 *
 * @author Benedikt Volkmer
 *         Created on 11/4/16.
 */
public interface TimelineService<E extends ScheduledElement> {

    /**
     * Get the timeline of today.
     *
     * @return timeline with only elements of today
     */
    Timeline<E> getToday();

    /**
     * Get the timeline of a specific date.
     *
     * @param date date of retrieval
     *
     * @return timeline with only elements of date
     */
    Timeline<E> getDate(LocalDate date);

    /**
     * Get the timeline of the given time span.
     *
     * @param start start of the time span
     * @param end   end of the time span
     *
     * @return timeline with only elements between start and end including elements overlapping start or end
     */
    Timeline<E> getTimeSpan(LocalDateTime start, LocalDateTime end);

}
