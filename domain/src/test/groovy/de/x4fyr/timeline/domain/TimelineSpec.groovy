package de.x4fyr.timeline.domain

import de.x4fyr.timeline.domain.elements.ScheduledElement
import spock.lang.Specification

import org.joda.time.Duration
import org.joda.time.LocalDateTime

/**
 * @author Benedikt Volkmer
 * Created on 11/1/16.
 */
class TimelineSpec extends Specification {

    def "test empty constructor"() {
        when:
        Timeline<ScheduledElement> timeline = new Timeline<>()
        then:
        timeline.size() == 0
    }

    def "test add, addFirst and addLast, addIndexed, push"() { // All should have the same effect
        given:
        ScheduledElement preset1 = Stub(ScheduledElement.class) {
            getStart() >> new LocalDateTime(a)
        }
        ScheduledElement preset2 = Stub(ScheduledElement.class) {
            getStart() >> new LocalDateTime(b)
        }
        ScheduledElement addition = Stub(ScheduledElement.class) {
            getStart() >> new LocalDateTime(c)
        }
        Timeline<ScheduledElement> add = new Timeline<>(preset1, preset2)
        Timeline<ScheduledElement> addFirst = new Timeline<>(preset1, preset2)
        Timeline<ScheduledElement> addLast = new Timeline<>(preset1, preset2)
        Timeline<ScheduledElement> addIndexed = new Timeline<>(preset1, preset2)
        Timeline<ScheduledElement> push = new Timeline<>(preset1, preset2)
        when:
        add.add(addition)
        addFirst.addFirst(addition)
        addLast.addLast(addition)
        addIndexed.add(3, addition)
        push.push(addition)
        then:
        assert add.get(position) == addition
        assert addFirst.get(position) == addition
        assert addLast.get(position) == addition
        assert addIndexed.get(position) == addition
        assert push.get(position) == addition
        where:
        a | b | c
        1 | 2 | 3
        3 | 1 | 2
        2 | 3 | 1

        position = c - 1
    }

    def "test addAll and collection constructor"() {
        given:
        Timeline<ScheduledElement> addAll = new Timeline<>()
        Timeline<ScheduledElement> addAllIndexed = new Timeline<>()
        Timeline<ScheduledElement> constructor
        ScheduledElement elementA = Stub(ScheduledElement.class) {
            getStart() >> new LocalDateTime(a)
        }
        ScheduledElement elementB = Stub(ScheduledElement.class) {
            getStart() >> new LocalDateTime(b)
        }
        ScheduledElement elementC = Stub(ScheduledElement.class) {
            getStart() >> new LocalDateTime(c)
        }
        List list = [elementA, elementB, elementC]
        when:
        addAll.addAll(list)
        addAllIndexed.addAll(2, list)
        constructor = new Timeline<>(list)
        then:
        assert addAll.get(positionA) == elementA
        assert addAll.get(positionB) == elementB
        assert addAll.get(positionC) == elementC
        assert addAllIndexed.get(positionA) == elementA
        assert addAllIndexed.get(positionB) == elementB
        assert addAllIndexed.get(positionC) == elementC
        assert constructor.get(positionA) == elementA
        assert constructor.get(positionB) == elementB
        assert constructor.get(positionC) == elementC
        where:
        a | b | c
        1 | 2 | 3
        3 | 1 | 2
        2 | 3 | 1
        positionA = a - 1
        positionB = b - 1
        positionC = c - 1
    }

    def "test set"() {
        given:
        LocalDateTime start = new LocalDateTime(0)
        Duration duration = Duration.ZERO
        String title = "title"
        String notes = "notes"
        ScheduledElement first = new ScheduledElement(start, duration, title, notes, null)
        ScheduledElement second = new ScheduledElement(start.plusDays(1), duration, title, notes, null)
        ScheduledElement third = new ScheduledElement(start.plusDays(2), duration, title, notes, null)
        ScheduledElement fourth = new ScheduledElement(start.plusDays(3), duration, title, notes, null)
        Timeline<ScheduledElement> timeline = new Timeline(first, second, third)
        when:
        def result = timeline.set(1, fourth)
        then:
        assert result == second
        assert timeline.get(0) == first
        assert timeline.get(1) == third
        assert timeline.get(2) == fourth
        assert timeline.size() == 3
    }

}
