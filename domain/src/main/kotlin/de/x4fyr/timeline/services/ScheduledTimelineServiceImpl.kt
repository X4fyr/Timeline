package de.x4fyr.timeline.services

import de.x4fyr.timeline.adapter.ScheduleAdapter
import de.x4fyr.timeline.domain.Timeline
import de.x4fyr.timeline.domain.elements.ScheduledElement
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * @author Benedikt Volkmer
 * *         Created on 11/14/16.
 */
class ScheduledTimelineServiceImpl internal constructor(private val adapter: ScheduleAdapter) : TimelineService<ScheduledElement> {

    /**
     * Get the timeline of today.
     * @return timeline with only elements of today
     */
    override fun getToday(): Timeline<ScheduledElement> = Timeline(
            adapter.getByTimeSpan(LocalDateTime.now().with(LocalTime.MIN), LocalDateTime.now().with(LocalTime.MAX)))

    /**
     * Get the timeline of a specific date.
     * @param date date of retrieval
     * @return timeline with only elements of date
     */
    override fun getDate(date: LocalDate): Timeline<ScheduledElement> {
        return Timeline(adapter.getByTimeSpan(date.atStartOfDay(), date.atTime(LocalTime.MAX)))
    }

    /**
     * Get the timeline of the given time span.
     * @param start start of the time span
     * @param end   end of the time span
     * @return timeline with only elements between start and end including elements overlapping start or end
     */
    override fun getTimeSpan(start: LocalDateTime, end: LocalDateTime): Timeline<ScheduledElement> {
        return Timeline(adapter.getByTimeSpan(start, end))
    }
}
