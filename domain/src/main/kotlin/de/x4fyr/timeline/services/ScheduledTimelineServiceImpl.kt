package de.x4fyr.timeline.services

import de.x4fyr.timeline.adapter.ScheduleAdapter
import de.x4fyr.timeline.domain.Timeline
import de.x4fyr.timeline.domain.elements.ScheduledElement
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.joda.time.LocalTime

/**
 * @author Benedikt Volkmer
 * *         Created on 11/14/16.
 */
class ScheduledTimelineServiceImpl internal constructor(private val adapter: ScheduleAdapter) : ScheduledTimelineService {

    override fun expandForward(timeline: Timeline<ScheduledElement>, count: Long): Timeline<ScheduledElement> {
        timeline.addAll(adapter.getNext(timeline.last, count))
        return timeline
    }

    override fun expandBackward(timeline: Timeline<ScheduledElement>, count: Long): Timeline<ScheduledElement> {
        timeline.addAll(adapter.getPrev(timeline.first, count))
        return timeline
    }

    /**
     * Get the timeline of today.
     * @return timeline with only elements of today
     */
    override fun getToday(): Timeline<ScheduledElement> = Timeline(
            adapter.getByTimeSpan(LocalDateTime.now().withTime(0, 0, 0, 0), LocalDateTime.now().withTime(23, 59, 59,
                    999))
    )

    /**
     * Get the timeline of a specific date.
     * @param date date of retrieval
     * @return timeline with only elements of date
     */
    override fun getDate(date: LocalDate): Timeline<ScheduledElement> = Timeline(
            adapter.getByTimeSpan(date.toDateTimeAtStartOfDay().toLocalDateTime(),
                    date.toLocalDateTime(LocalTime(23, 59, 59, 999)))
    )

    /**
     * Get the timeline of the given time span.
     * @param start start of the time span
     * @param end   end of the time span
     * @return timeline with only elements between start and end including elements overlapping start or end
     */
    override fun getTimeSpan(start: LocalDateTime, end: LocalDateTime): Timeline<ScheduledElement> = Timeline(
            adapter.getByTimeSpan(start, end)
    )
}
