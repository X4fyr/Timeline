package timeline.services

import timeline.adapter.ScheduleAdapter
import timeline.domain.Timeline
import timeline.domain.elements.ScheduledElement
import utils.date.DateTime
import utils.date.DateOnly

/**
 * @author Benedikt Volkmer
 * *         Created on 11/14/16.
 */
class ScheduleServiceImpl internal constructor(private val adapter: ScheduleAdapter) : ScheduleService {

    override fun expandForward(timeline: Timeline<ScheduledElement>, count: Long): Timeline<ScheduledElement> {
        timeline.addAll(adapter.getNext(timeline.last(), count))
        return timeline
    }

    override fun expandBackward(timeline: Timeline<ScheduledElement>, count: Long): Timeline<ScheduledElement> {
        timeline.addAll(adapter.getPrev(timeline.first(), count))
        return timeline
    }

    /**
     * Get the timeline of today.
     * @return timeline with only elements of today
     */
    override fun getToday(): Timeline<ScheduledElement> = Timeline(
            adapter.getByTimeSpan(DateTime.now().atStartOfDay(), DateTime.now().atEndOfDay())
    )

    /**
     * Get the timeline of a specific date.
     * @param date date of retrieval
     * @return timeline with only elements of date
     */
    override fun getDate(date: DateOnly): Timeline<ScheduledElement> = Timeline(
            adapter.getByTimeSpan(date.atStartOfDay(), date.atEndOfDay()))

    /**
     * Get the timeline of the given time span.
     * @param start start of the time span
     * @param end   end of the time span
     * @return timeline with only elements between start and end including elements overlapping start or end
     */
    override fun getTimeSpan(start: DateTime, end: DateTime): Timeline<ScheduledElement> = Timeline(
            adapter.getByTimeSpan(start, end)
    )
}
