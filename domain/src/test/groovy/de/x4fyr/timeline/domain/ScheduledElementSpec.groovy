package de.x4fyr.timeline.domain

import spock.lang.Specification

import java.time.Duration
import java.time.LocalDateTime

/**
 * @author Benedikt Volkmer
 * Created on 11/4/16.
 */
class ScheduledElementSpec extends Specification {

    def "lombok non-tested methods"() {
        given:
        LocalDateTime start = LocalDateTime.MIN
        Duration duration = Duration.ZERO
        String title = UUID.randomUUID().toString()
        String notes = UUID.randomUUID().toString()
        ScheduledElement element = new ScheduledElement(start, duration, title, notes)
        expect:
        element.getStart()
        element.setStart(start)
        element.getDuration()
        element.setDuration(duration)
        element.setExternalUuid(UUID.randomUUID())
        element.getExternalUuid()
        element.toString()
        element.equals(element)
        element.canEqual(element)
        element.hashCode()

    }

    def "constructor test"() {
        given:
        LocalDateTime start = LocalDateTime.MIN
        Duration duration = Duration.ZERO
        String title = UUID.randomUUID().toString()
        String notes = UUID.randomUUID().toString()
        when:
        ScheduledElement element = new ScheduledElement(start, duration, title, notes)
        then:
        assert element.start == start
        assert element.duration == duration
        assert element.title == title
        assert element.notes == notes
        assert element.externalUuid == null
    }
}
