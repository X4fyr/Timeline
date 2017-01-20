package timeline.services

import timeline.domain.Timeline
import timeline.domain.elements.ScheduledElement
import utils.date.DateTime
import utils.date.DateOnly

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
    fun getDate(date: DateOnly): Timeline<ScheduledElement>

    /**
     * Get the timeline of the given time span.
     * @param start start of the time span
     * @param end   end of the time span
     * @return timeline with only elements between start and end including elements overlapping start or end
     */
    fun getTimeSpan(start: DateTime, end: DateTime): Timeline<ScheduledElement>

    fun expandForward(timeline: Timeline<ScheduledElement>, count: Long): Timeline<ScheduledElement>

    fun expandBackward(timeline: Timeline<ScheduledElement>, count: Long): Timeline<ScheduledElement>

}
