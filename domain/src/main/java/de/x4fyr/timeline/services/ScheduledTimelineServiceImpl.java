package de.x4fyr.timeline.services;

import de.x4fyr.timeline.adapter.ScheduleAdapter;
import de.x4fyr.timeline.domain.Timeline;
import de.x4fyr.timeline.domain.elements.ScheduledElement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author Benedikt Volkmer
 *         Created on 11/14/16.
 */
public class ScheduledTimelineServiceImpl implements TimelineService<ScheduledElement> {

    private final ScheduleAdapter adapter;

    ScheduledTimelineServiceImpl(ScheduleAdapter scheduleAdapter) {
        this.adapter = scheduleAdapter;
    }

    /**
     * Get the timeline of today.
     *
     * @return timeline with only elements of today
     */
    @Override
    public Timeline<ScheduledElement> getToday() {
        return new Timeline<>(adapter.getByTimeSpan(LocalDateTime.now().with(LocalTime.MIN),
                LocalDateTime.now().with(LocalTime.MAX)));

    }

    /**
     * Get the timeline of a specific date.
     *
     * @param date date of retrieval
     *
     * @return timeline with only elements of date
     */
    @Override
    public Timeline<ScheduledElement> getDate(LocalDate date) {
        return new Timeline<>(adapter.getByTimeSpan(date.atStartOfDay(), date.atTime(LocalTime.MAX)));
    }

    /**
     * Get the timeline of the given time span.
     *
     * @param start start of the time span
     * @param end   end of the time span
     *
     * @return timeline with only elements between start and end including elements overlapping start or end
     */
    @Override
    public Timeline<ScheduledElement> getTimeSpan(LocalDateTime start, LocalDateTime end) {
        return new Timeline<>(adapter.getByTimeSpan(start, end));
    }
}
