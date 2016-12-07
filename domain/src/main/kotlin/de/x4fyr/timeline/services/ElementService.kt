package de.x4fyr.timeline.services

import de.x4fyr.timeline.domain.elements.ExternalElement
import de.x4fyr.timeline.domain.elements.ScheduledElement
import de.x4fyr.timeline.domain.elements.TodoElement
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * Service interface serving the use-cases of elements.
 * @author Benedikt Volkmer
 *          Created on 11/4/16.
 */
interface ElementService {

    /**
     * Save sheduled element to persistence.
     * @param element element to save
     * @return saved element
     */
    fun saveElement(element: ScheduledElement): ScheduledElement

    /**
     * Save todo element to persistence
     * @param element element to save
     * @return saved element
     */
    fun saveElement(element: TodoElement): TodoElement

    /**
     * Schedule a TodoElement.
     * @param todoElement element to schedule.
     * @param startTime start time of the ScheduleElement. If TodoElement.plannedDate is set this must not be null
     * @param startDate start date of the ScheduleElement. If TodoElement.plannedDate is set this must not be null
     * @return A ScheduledElement made of the date in the TodoElement
     */

    fun scheduleElement(todoElement: TodoElement, startTime: LocalTime?, startDate: LocalDateTime?): ScheduledElement

    /**
     * Unschedule an ScheduledElement to TodoElement.
     * @param scheduledElement ScheduledElement to unschedule.
     * @param keepDate         If the date should be kept in the TodoElement
     * @return A TodoElement of null if it was an external element
     */
    fun unscheduleElement(scheduledElement: ScheduledElement, keepDate: Boolean?): TodoElement?

    /**
     * @param externalElement Schedule an ExternalElement
     * @return ExternalElement
     */
    fun scheduleExternalElement(externalElement: ExternalElement): ScheduledElement
}
