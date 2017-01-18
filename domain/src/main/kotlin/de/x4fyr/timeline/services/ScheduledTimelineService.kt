package de.x4fyr.timeline.services

import de.x4fyr.timeline.domain.Timeline
import de.x4fyr.timeline.domain.elements.ScheduledElement

import org.joda.time.LocalDate
import org.joda.time.LocalDateTime

/**
 * Service interface serving the use-cases on a timeline.

 * @param <E> Type of elements in the timeline
 * @author Benedikt Volkmer
 *         Created on 11/4/16.
</E> */
interface ScheduledTimelineService {

    /**
     * Get the timeline of today.
     * @return timeline with only elements of today
     */
    fun getToday(): Timeline<ScheduledElement>

    /**
     * Get the timeline of a specific date.
     * @param date date of retrieval
     * @return timeline with only elements of date
     */
    fun getDate(date: LocalDate): Timeline<ScheduledElement>

    /**
     * Get the timeline of the given time span.
     * @param start start of the time span
     * @param end   end of the time span
     * @return timeline with only elements between start and end including elements overlapping start or end
     */
    fun getTimeSpan(start: LocalDateTime, end: LocalDateTime): Timeline<ScheduledElement>

    fun expandForward(timeline: Timeline<ScheduledElement>, count: Long): Timeline<ScheduledElement>

    fun expandBackward(timeline: Timeline<ScheduledElement>, count: Long): Timeline<ScheduledElement>

}
