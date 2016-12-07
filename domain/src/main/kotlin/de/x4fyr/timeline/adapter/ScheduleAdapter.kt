package de.x4fyr.timeline.adapter

import de.x4fyr.timeline.domain.elements.ScheduledElement

import java.time.LocalDateTime

/**
 * @author Benedikt Volkmer
 *         Created on 11/14/16.
 */
interface ScheduleAdapter {

    fun saveToSchedule(element: ScheduledElement): ScheduledElement

    fun deleteFromSchedule(element: ScheduledElement)

    fun getByTimeSpan(start: LocalDateTime, end: LocalDateTime): Collection<ScheduledElement>
}
