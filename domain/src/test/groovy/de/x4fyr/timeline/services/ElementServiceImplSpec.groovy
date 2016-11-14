package de.x4fyr.timeline.services

import de.x4fyr.timeline.adapter.ExternalScheduleAdapter
import de.x4fyr.timeline.adapter.ScheduleAdapter
import de.x4fyr.timeline.adapter.TodoListAdapter
import de.x4fyr.timeline.domain.elements.ExternalElement
import de.x4fyr.timeline.domain.elements.ScheduledElement
import de.x4fyr.timeline.domain.elements.TodoElement
import spock.lang.Specification

import java.time.Duration
import java.time.LocalDateTime

/**
 * @author Benedikt Volkmer
 * Created on 11/14/16.
 */
class ElementServiceImplSpec extends Specification {

    static TITLE = "title"
    static NOTES = "notes"
    static DATETIME = LocalDateTime.MIN
    static DURATION = Duration.ZERO
    static EX_UUID = UUID.randomUUID()

    def ScheduleAdapter scheduleAdapter
    def ExternalScheduleAdapter externalScheduleAdapter
    def TodoListAdapter todoListAdapter
    def ElementServiceImpl service
    def ScheduledElement scheduledElement
    def ExternalElement externalElement
    def TodoElement todoElement

    def setup() {
        scheduleAdapter = Mock(ScheduleAdapter.class)
        externalScheduleAdapter = Mock(ExternalScheduleAdapter.class)
        todoListAdapter = Mock(TodoListAdapter.class)
        service = new ElementServiceImpl(scheduleAdapter, externalScheduleAdapter, todoListAdapter)
        scheduledElement = Stub(ScheduledElement.class)
        externalElement = Stub(ExternalElement.class)
        todoElement = Stub(TodoElement.class)
    }

    def "test saveElement"() {
        when:
        def result1 = service.saveElement(scheduledElement)
        def result2 = service.saveElement(todoElement)
        then:
        1 * scheduleAdapter.saveToSchedule(scheduledElement) >> scheduledElement;
        assert result1 == scheduledElement
        then:
        1 * todoListAdapter.saveToToDoList(todoElement) >> todoElement;
        assert result2 == todoElement;
        then:
        0 * _
    }

    def "test scheduleElement"() {
        given:
        todoElement.getTitle() >> TITLE
        todoElement.getNotes() >> NOTES
        todoElement.getPlannedDate() >> DATETIME.toLocalDate()
        todoElement.getPlannedDuration() >> DURATION
        when:
        def result = service.scheduleElement(todoElement, DATETIME.toLocalTime())
        then:
        1 * todoListAdapter.deleteFromTodoList(todoElement)
        1 * scheduleAdapter.saveToSchedule({
            it.title == TITLE &&
                    it.notes == NOTES &&
                    it.start == DATETIME &&
                    it.duration == DURATION &&
                    it.externalUUID == null
        }) >> scheduledElement
        assert result == scheduledElement
    }

    def "test unscheduleElement with a non external element"() {
        given: "a non external linked element"
        scheduledElement.getTitle() >> TITLE
        scheduledElement.getNotes() >> NOTES
        scheduledElement.getStart() >> DATETIME
        scheduledElement.getDuration() >> DURATION
        scheduledElement.getExternalUUID() >> null
        when:
        def result1 = service.unscheduleElement(scheduledElement, true)
        def result2 = service.unscheduleElement(scheduledElement, false)
        then: "delete from schedule and save to ToDo list"
        1 * todoListAdapter.saveToToDoList({
            it.title == TITLE &&
                    it.notes == NOTES &&
                    it.plannedDate == DATETIME.toLocalDate() &&
                    it.plannedDuration == DURATION
        }) >> todoElement
        1 * scheduleAdapter.deleteFromSchedule(scheduledElement)
        assert result1 == todoElement
        then:
        1 * todoListAdapter.saveToToDoList({
            it.title == TITLE &&
                    it.notes == NOTES &&
                    it.plannedDate == null &&
                    it.plannedDuration == DURATION
        }) >> todoElement
        1 * scheduleAdapter.deleteFromSchedule(scheduledElement)
        assert result2 == todoElement
    }

    def "test unscheduleElement with external element"() {
        given:
        scheduledElement.getTitle() >> TITLE
        scheduledElement.getNotes() >> NOTES
        scheduledElement.getStart() >> DATETIME
        scheduledElement.getDuration() >> DURATION
        scheduledElement.getExternalUUID() >> EX_UUID
        when:
        def result1 = service.unscheduleElement(scheduledElement, true)
        def result2 = service.unscheduleElement(scheduledElement, false)
        then:
        2 * externalScheduleAdapter.unsetExternalElementScheduled({
            it.title == TITLE &&
                    it.notes == NOTES &&
                    it.start == DATETIME &&
                    it.duration == DURATION &&
                    it.externalUUID == EX_UUID
        })
        2 * scheduleAdapter.deleteFromSchedule(scheduledElement)
        assert result1 == null && result2 == null
    }

    def "test scheduleExternalElement"() {
        given: "an unscheduled element"
        externalElement.getTitle() >> TITLE
        externalElement.getNotes() >> NOTES
        externalElement.getStart() >> DATETIME
        externalElement.getDuration() >> DURATION
        externalElement.getExternalUUID() >> EX_UUID
        externalElement.isScheduled() >> false
        when:
        def result = service.scheduleExternalElement(externalElement)
        then:
        1 * externalScheduleAdapter.setExternalElementScheduled(externalElement)
        1 * scheduleAdapter.saveToSchedule({
            it.title == TITLE &&
                    it.notes == NOTES &&
                    it.start == DATETIME &&
                    it.duration == DURATION &&
                    it.externalUUID == EX_UUID
        }) >> scheduledElement
        assert result == scheduledElement
    }

    def "test scheduleExternalElement with already scheduled element"() {
        given:
        externalElement.isScheduled() >> true
        when:
        def result = service.scheduleExternalElement(externalElement)
        then:
        0 * externalScheduleAdapter._(_)
        0 * scheduleAdapter._(_)
        assert result == externalElement;
    }
}
