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
     * What is considered as the last hour of the day for default interface methods.
     */
    int LAST_HOUR = 23;
    /**
     * What is considered as the last minute of the day for default interface methods.
     */
    int LAST_MINUTE = 59;

    /**
     * Get the timeline of today.
     *
     * <p>Defaults to a call of getDate(LocalDate.now())
     *
     * @return timeline with only elements of today
     */
    default Timeline<E> getToday() {
        return this.getDate(LocalDate.now());
    }

    /**
     * Get the timeline of a specific date.
     *
     * <p>defaults to getTimeSpan of startOfDay to 23:59 of the current day
     *
     * @param date date of retrieval
     *
     * @return timeline with only elements of date
     */
    default Timeline<E> getDate(LocalDate date) {
        return getTimeSpan(date.atStartOfDay(), date.atTime(LAST_HOUR, LAST_MINUTE));
    }

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
