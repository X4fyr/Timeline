package de.x4fyr.timeline.adapter;

import de.x4fyr.timeline.domain.elements.ScheduledElement;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Benedikt Volkmer
 *         Created on 11/14/16.
 */
public interface ScheduleAdapter {

    ScheduledElement saveToSchedule(ScheduledElement element);

    void deleteFromSchedule(ScheduledElement element);

    Collection<ScheduledElement> getByTimeSpan(LocalDateTime start, LocalDateTime end);
}
