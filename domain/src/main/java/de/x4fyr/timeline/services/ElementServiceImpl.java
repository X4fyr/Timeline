package de.x4fyr.timeline.services;

import de.x4fyr.timeline.adapter.ExternalScheduleAdapter;
import de.x4fyr.timeline.adapter.ScheduleAdapter;
import de.x4fyr.timeline.adapter.TodoListAdapter;
import de.x4fyr.timeline.domain.elements.Element;
import de.x4fyr.timeline.domain.elements.ExternalElement;
import de.x4fyr.timeline.domain.elements.ScheduledElement;
import de.x4fyr.timeline.domain.elements.TodoElement;

import java.time.LocalTime;

/**
 * @author Benedikt Volkmer
 *         Created on 11/14/16.
 */
public class ElementServiceImpl implements ElementService {

    private final ScheduleAdapter scheduleAdapter;
    private final ExternalScheduleAdapter externalScheduleAdapter;
    private final TodoListAdapter todoListAdapter;

    public ElementServiceImpl(ScheduleAdapter scheduleAdapter, ExternalScheduleAdapter externalScheduleAdapter,
                              TodoListAdapter todoListAdapter) {
        this.scheduleAdapter = scheduleAdapter;
        this.externalScheduleAdapter = externalScheduleAdapter;
        this.todoListAdapter = todoListAdapter;
    }

    /**
     * Save element to persistence.
     *
     * @param element Element to save
     *
     * @return saved Element
     */
    @Override
    public Element saveElement(Element element) {
        Element result = null;
        if (element instanceof ScheduledElement) {
            result = scheduleAdapter.saveToSchedule((ScheduledElement) element);
        } else if (element instanceof TodoElement) {
            result = todoListAdapter.saveToToDoList((TodoElement) element);
        }
        return result;
    }

    /**
     * Schedule a TodoElement.
     *
     * @param todoElement element to schedule.
     * @param start       start time of the ScheduleElement.
     *
     * @return A ScheduledElement made of the date in the TodoElement
     */
    @Override
    public ScheduledElement scheduleElement(TodoElement todoElement, LocalTime start) {
        ScheduledElement scheduledElement = new ScheduledElement(todoElement.getPlannedDate().atTime(start), todoElement
                .getPlannedDuration(), todoElement.getTitle(), todoElement.getNotes());
        todoListAdapter.deleteFromTodoList(todoElement);
        return scheduleAdapter.saveToSchedule(scheduledElement);
    }

    /**
     * Unschedule an ScheduledElement to TodoElement.
     *
     * @param scheduledElement ScheduledElement to unschedule.
     * @param keepDate         If the date should be kept in the TodoElement
     *
     * @return A TodoElement or null if it was an ExternalElement
     */
    @Override
    public TodoElement unscheduleElement(ScheduledElement scheduledElement, Boolean keepDate) {
        TodoElement result = null;
        if (scheduledElement.getExternalUUID() != null) {
            ExternalElement externalElement = new ExternalElement(scheduledElement.getExternalUUID(),
                    scheduledElement.getStart(), scheduledElement.getDuration(), scheduledElement.getTitle(),
                    scheduledElement.getNotes());
            externalScheduleAdapter.unsetExternalElementScheduled(externalElement);
        } else {
            TodoElement todoElement = new TodoElement(scheduledElement.getTitle(), scheduledElement.getNotes());
            todoElement.setPlannedDuration(scheduledElement.getDuration());
            if (keepDate) {
                todoElement.setPlannedDate(scheduledElement.getStart().toLocalDate());
            }
            result = todoListAdapter.saveToToDoList(todoElement);
        }
        scheduleAdapter.deleteFromSchedule(scheduledElement);
        return result;
    }

    /**
     * @param externalElement Schedule an ExternalElement
     *
     * @return ExternalElement
     */
    @Override
    public ScheduledElement scheduleExternalElement(ExternalElement externalElement) {
        if (externalElement.isScheduled()) {
            return externalElement;
        }
        externalScheduleAdapter.setExternalElementScheduled(externalElement);
        return scheduleAdapter.saveToSchedule(externalElement);
    }
}
