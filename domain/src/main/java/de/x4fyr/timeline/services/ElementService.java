package de.x4fyr.timeline.services;

import de.x4fyr.timeline.domain.elements.Element;
import de.x4fyr.timeline.domain.elements.ExternalElement;
import de.x4fyr.timeline.domain.elements.ScheduledElement;
import de.x4fyr.timeline.domain.elements.TodoElement;

import java.time.LocalTime;

/**
 * Service interface serving the use-cases of elements.
 *
 * @author Benedikt Volkmer
 *         Created on 11/4/16.
 */
public interface ElementService {

    /**
     * Save element to persistence.
     *
     * @param element Element to save
     *
     * @return saved Element
     */
    Element saveElement(Element element);

    /**
     * Schedule a TodoElement.
     *
     * @param todoElement element to schedule.
     * @param start       start time of the ScheduleElement.
     *
     * @return A ScheduledElement made of the date in the TodoElement
     */
    ScheduledElement scheduleElement(TodoElement todoElement, LocalTime start);

    /**
     * Unschedule an ScheduledElement to TodoElement.
     *
     * @param scheduledElement ScheduledElement to unschedule.
     * @param keepDate         If the date should be kept in the TodoElement
     *
     * @return A TodoElement of null if it was an external element
     */
    TodoElement unscheduleElement(ScheduledElement scheduledElement, Boolean keepDate);

    /**
     * @param externalElement Schedule an ExternalElement
     *
     * @return ExternalElement
     */
    ScheduledElement scheduleExternalElement(ExternalElement externalElement);
}
