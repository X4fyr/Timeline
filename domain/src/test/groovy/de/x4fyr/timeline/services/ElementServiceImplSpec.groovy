package de.x4fyr.timeline.services

import de.x4fyr.timeline.adapter.ExternalScheduleAdapter
import de.x4fyr.timeline.adapter.ScheduleAdapter
import de.x4fyr.timeline.adapter.TodoListAdapter
import de.x4fyr.timeline.domain.elements.ExternalElement
import de.x4fyr.timeline.domain.elements.ScheduledElement
import de.x4fyr.timeline.domain.elements.TodoElement
import spock.lang.Specification

import org.joda.time.Duration
import org.joda.time.LocalDateTime

/**
 * @author Benedikt Volkmer
 * Created on 11/14/16.
 */
class ElementServiceImplSpec extends Specification {

    private static final Random random = new Random()
    static Long ID = random.nextLong()
    static String TITLE = "title"
    static String NOTES = "notes"
    static LocalDateTime DATETIME = new LocalDateTime(0)
    static Duration DURATION = Duration.ZERO
    static Long EX_ID = random.nextLong()

    ScheduleAdapter scheduleAdapter
    ExternalScheduleAdapter externalScheduleAdapter
    TodoListAdapter todoListAdapter
    ElementServiceImpl service
    ScheduledElement scheduledElement
    ExternalElement externalElement
    TodoElement todoElement

    @SuppressWarnings("GroovyUnusedDeclaration")
    def setup() {
        scheduleAdapter = Mock(ScheduleAdapter.class)
        externalScheduleAdapter = Mock(ExternalScheduleAdapter.class)
        todoListAdapter = Mock(TodoListAdapter.class)
        service = new ElementServiceImpl(scheduleAdapter, externalScheduleAdapter, todoListAdapter)
        scheduledElement = new ScheduledElement(null, "", "", LocalDateTime.now(), Duration.ZERO, null)
        externalElement = new ExternalElement(null, "", "", LocalDateTime.now(), Duration.ZERO, null)
        todoElement = new TodoElement(null, "", "", null, null)
    }

    def "test saveElement"() {
        when:
        def result1 = service.saveElement(scheduledElement)
        def result2 = service.saveElement(todoElement)
        then:
        1 * scheduleAdapter.saveToSchedule(scheduledElement) >> scheduledElement
        assert result1 == scheduledElement
        then:
        1 * todoListAdapter.saveToToDoList(todoElement) >> todoElement
        assert result2 == todoElement
        then:
        0 * _
    }

    def "test scheduleElement"() {
        given:
        todoElement = new TodoElement(null, TITLE, NOTES, DATETIME.toLocalDate(), DURATION)
        when:
        def result = service.scheduleElement(todoElement, DATETIME.toLocalTime(), null)
        then:
        1 * todoListAdapter.deleteFromTodoList(todoElement)
        1 * scheduleAdapter.saveToSchedule({
            it.title == TITLE &&
                    it.notes == NOTES &&
                    it.start == DATETIME &&
                    it.duration == DURATION &&
                    it.externalId == null
        }) >> scheduledElement
        assert result == scheduledElement
    }

    def "test unscheduleElement with a non external element"() {
        given: "a non external linked element"
        scheduledElement = new ScheduledElement(null, TITLE, NOTES, DATETIME, DURATION, null)
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
        scheduledElement = new ScheduledElement(null, TITLE, NOTES, DATETIME, DURATION, EX_ID)
        when:
        def result1 = service.unscheduleElement(scheduledElement, true)
        def result2 = service.unscheduleElement(scheduledElement, false)
        then:
        2 * externalScheduleAdapter.unsetExternalElementScheduled(EX_ID)
        2 * scheduleAdapter.deleteFromSchedule(scheduledElement)
        assert result1 == null && result2 == null
    }

    def "test scheduleExternalElement"() {
        given: "an unscheduled element"
        externalElement = new ExternalElement(EX_ID, TITLE, NOTES, DATETIME, DURATION, null)
        when:
        def result = service.scheduleExternalElement(externalElement)
        then:
        1 * externalScheduleAdapter.setExternalElementScheduled(EX_ID)
        1 * scheduleAdapter.saveToSchedule({
            it.title == TITLE &&
                    it.notes == NOTES &&
                    it.start == DATETIME &&
                    it.duration == DURATION &&
                    it.id == null &&
                    it.externalId == EX_ID
        }) >> scheduledElement
        assert result == scheduledElement
    }

    def "test scheduleExternalElement with already scheduled element"() {
        given:
        externalElement = new ExternalElement(EX_ID, TITLE, NOTES, DATETIME, DURATION, ID)
        when:
        def result = service.scheduleExternalElement(externalElement)
        then:
        0 * externalScheduleAdapter._(_)
        0 * scheduleAdapter._(_)
        assert result.id == ID
        assert result.title == TITLE
        assert result.start == DATETIME
        assert result.duration == DURATION
        assert result.externalId == EX_ID
    }
}
