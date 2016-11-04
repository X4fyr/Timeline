package de.x4fyr.timeline.domain

import spock.lang.Specification

import java.time.Duration
import java.time.LocalDate

/**
 * @author Benedikt Volkmer
 * Created on 11/4/16.
 */
class TodoElementSpec extends Specification {

    def "lombok non-tested methods"() {
        given:
        String title = UUID.randomUUID().toString()
        String notes = UUID.randomUUID().toString()
        TodoElement element = new TodoElement(title, notes)
        expect:
        element.setPlannedDate(LocalDate.MIN)
        element.getPlannedDate()
        element.setPlannedDuration(Duration.ZERO)
        element.getPlannedDate()
        element.toString()
        element.equals(element)
        element.canEqual(element)
        element.hashCode()
    }

    def "constructor test"() {
        given:
        String title = UUID.randomUUID().toString()
        String notes = UUID.randomUUID().toString()
        when:
        TodoElement element = new TodoElement(title, notes)
        then:
        assert element.title == title
        assert element.notes == notes
        assert element.plannedDate == null
        assert element.plannedDuration == null
    }
}
