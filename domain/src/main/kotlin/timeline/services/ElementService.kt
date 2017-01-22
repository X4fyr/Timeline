package timeline.services

import timeline.domain.elements.ExternalElement
import timeline.domain.elements.ScheduledElement
import timeline.domain.elements.TodoElement
import utils.date.DateTime
import utils.date.Time

/**
 * Service interface serving the use-cases of elements.
 * @author Benedikt Volkmer
 *          Created on 11/4/16.
 */
interface ElementService {

    /**
     * Save scheduled element to persistence.
     * @param element element to save
     * @return saved element
     */
    @JsName("saveScheduledElement")
    fun saveScheduledElement(element: ScheduledElement): ScheduledElement

    /**
     * Save todo element to persistence
     * @param element element to save
     * @return saved element
     */
    @JsName("saveTodoElement")
    fun saveTodoElement(element: TodoElement): TodoElement

    /**
     * Schedule a TodoElement.
     * @param todoElement element to schedule.
     * @param startTime start time of the ScheduleElement. If TodoElement.plannedDate is set this must not be null
     * @param startDate start date of the ScheduleElement. If TodoElement.plannedDate is set this must not be null
     * @return A ScheduledElement made of the date in the TodoElement
     */
    @JsName("scheduleElement")
    fun scheduleElement(todoElement: TodoElement, startTime: Time?, startDate: DateTime?): ScheduledElement

    /**
     * Unschedule an ScheduledElement to TodoElement.
     * @param scheduledElement ScheduledElement to unschedule.
     * @param keepDate         If the date should be kept in the TodoElement
     * @return A TodoElement of null if it was an external element
     */
    @JsName("unscheduleElement")
    fun unscheduleElement(scheduledElement: ScheduledElement, keepDate: Boolean = true): TodoElement?

    /**
     * @param externalElement Schedule an ExternalElement
     * @return ExternalElement
     */
    @JsName("scheduleExternalElement")
    fun scheduleExternalElement(externalElement: ExternalElement): ScheduledElement
}
