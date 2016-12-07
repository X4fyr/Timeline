package de.x4fyr.timeline.services

import de.x4fyr.timeline.adapter.ExternalScheduleAdapter
import de.x4fyr.timeline.adapter.ScheduleAdapter
import de.x4fyr.timeline.adapter.TodoListAdapter
import de.x4fyr.timeline.domain.elements.ExternalElement
import de.x4fyr.timeline.domain.elements.ScheduledElement
import de.x4fyr.timeline.domain.elements.TodoElement
import java.time.LocalDateTime

import java.time.LocalTime

/**
 * @author Benedikt Volkmer
 *         Created on 11/14/16.
 */
class ElementServiceImpl(private val scheduleAdapter: ScheduleAdapter, private val externalScheduleAdapter: ExternalScheduleAdapter,
                         private val todoListAdapter: TodoListAdapter) : ElementService {

    /**
     * Save element to persistence.
     * @param element Element to save
     * @return saved Element
     */
    override fun saveElement(element: ScheduledElement): ScheduledElement = scheduleAdapter.saveToSchedule(element)

    /**
     * Save todo element to persistence
     * @param element element to save
     * @return saved element
     */
    override fun saveElement(element: TodoElement): TodoElement = todoListAdapter.saveToToDoList(element)

    /**
     * Schedule a TodoElement. TodoElement.plannedDuration must not be null
     * @param todoElement element to schedule.
     * @param startTime start time of the ScheduleElement. If TodoElement.plannedDate is set this must not be null
     * @param startDate start date of the ScheduleElement. If TodoElement.plannedDate is set this must not be null
     * @return A ScheduledElement made of the date in the TodoElement
     */
    override fun scheduleElement(todoElement: TodoElement, startTime: LocalTime?, startDate: LocalDateTime?):
            ScheduledElement {
        val scheduledElement = ScheduledElement(todoElement.plannedDate?.atTime(startTime!!) ?: startDate!!, todoElement
                .plannedDuration!!, todoElement.title, todoElement.notes, null)
        todoListAdapter.deleteFromTodoList(todoElement)
        return scheduleAdapter.saveToSchedule(scheduledElement)
    }

    /**
     * Unschedule an ScheduledElement to TodoElement.
     * @param scheduledElement ScheduledElement to unschedule.
     * @param keepDate         If the date should be kept in the TodoElement
     * @return A TodoElement or null if it was an ExternalElement
     */
    override fun unscheduleElement(scheduledElement: ScheduledElement, keepDate: Boolean?): TodoElement? {
        var result: TodoElement? = null
        if (scheduledElement.externalUUID != null) {
            val externalElement = ExternalElement(scheduledElement.start, scheduledElement.duration,
                    scheduledElement.title, scheduledElement.notes, scheduledElement.externalUUID!!)
            externalScheduleAdapter.unsetExternalElementScheduled(externalElement)
        } else {
            val todoElement = TodoElement(scheduledElement.title, scheduledElement.notes)
            todoElement.plannedDuration = scheduledElement.duration
            if (keepDate ?: false) {
                todoElement.plannedDate = scheduledElement.start.toLocalDate()
            }
            result = todoListAdapter.saveToToDoList(todoElement)
        }
        scheduleAdapter.deleteFromSchedule(scheduledElement)
        return result
    }

    /**
     * @param externalElement Schedule an ExternalElement
     * @return ExternalElement
     */
    override fun scheduleExternalElement(externalElement: ExternalElement): ScheduledElement {
        if (externalElement.isScheduled) {
            return externalElement
        }
        externalScheduleAdapter.setExternalElementScheduled(externalElement)
        return scheduleAdapter.saveToSchedule(externalElement)
    }
}
