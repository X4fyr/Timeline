package timeline.services

import timeline.adapter.ExternalScheduleAdapter
import timeline.adapter.ScheduleAdapter
import timeline.adapter.TodoListAdapter
import timeline.domain.elements.ExternalElement
import timeline.domain.elements.ScheduledElement
import timeline.domain.elements.TodoElement
import utils.date.DateTime
import utils.date.Time

/**
 * @author Benedikt Volkmer
 *         Created on 11/14/16.
 */
class ElementServiceImpl(private val scheduleAdapter: ScheduleAdapter,
                         private val externalScheduleAdapter: ExternalScheduleAdapter,
                         private val todoListAdapter: TodoListAdapter) : ElementService {

    /**
     * Save element to persistence.
     * @param element Element to save
     * @return saved Element
     */
    override fun saveScheduledElement(element: ScheduledElement): ScheduledElement = scheduleAdapter.saveToSchedule(element)

    /**
     * Save todo element to persistence
     * @param element element to save
     * @return saved element
     */
    override fun saveTodoElement(element: TodoElement): TodoElement = todoListAdapter.saveToToDoList(element)

    /**
     * Schedule a TodoElement. TodoElement.plannedDuration must not be null
     * @param todoElement element to schedule.
     * @param startTime start time of the ScheduleElement. If TodoElement.plannedDate is set this must not be null
     * @param startDate start date of the ScheduleElement. If TodoElement.plannedDate is set this must not be null
     * @return A ScheduledElement made of the date in the TodoElement
     */
    override fun scheduleElement(todoElement: TodoElement, startTime: Time?, startDate: DateTime?):
            ScheduledElement {
        val scheduledElement = ScheduledElement(
                start = todoElement.plannedDate?.at(startTime!!) ?: startDate!!,
                duration = todoElement.plannedDuration!!,
                title = todoElement.title,
                notes = todoElement.notes)
        todoListAdapter.deleteFromTodoList(todoElement)
        return scheduleAdapter.saveToSchedule(scheduledElement)
    }

    /**
     * Unschedule an ScheduledElement to TodoElement.
     * @param scheduledElement ScheduledElement to unschedule.
     * @param keepDate         If the date should be kept in the TodoElement
     * @return A TodoElement or null if it was an ExternalElement
     */
    override fun unscheduleElement(scheduledElement: ScheduledElement, keepDate: Boolean): TodoElement? {
        var result: TodoElement? = null
        if (scheduledElement.externalId != null) {
            externalScheduleAdapter.unsetExternalElementScheduled(scheduledElement.externalId)
        } else {
            val todoElement = TodoElement(title = scheduledElement.title, notes = scheduledElement.notes)
            todoElement.plannedDuration = scheduledElement.duration
            if (keepDate) {
                todoElement.plannedDate = scheduledElement.start.toDate()
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
        if (externalElement.id == null) throw UnsupportedOperationException("Needing element with ID")
        if (externalElement.scheduledId != null) {
            return ScheduledElement(
                    id = externalElement.scheduledId,
                    title = externalElement.title,
                    notes = externalElement.notes,
                    start = externalElement.start,
                    duration = externalElement.duration,
                    externalId = externalElement.id)
        } else {
            externalScheduleAdapter.setExternalElementScheduled(externalElement.id)
            return scheduleAdapter.saveToSchedule(ScheduledElement(
                    title = externalElement.title,
                    notes = externalElement.notes,
                    start = externalElement.start,
                    duration = externalElement.duration,
                    externalId = externalElement.id
            ))
        }
    }
}
