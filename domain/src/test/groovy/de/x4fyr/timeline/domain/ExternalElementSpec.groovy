package de.x4fyr.timeline.domain

import spock.lang.Specification

import java.time.Duration
import java.time.LocalDateTime

/**
 * @author Benedikt Volkmer
 * Created on 11/4/16.
 */
class ExternalElementSpec extends Specification {

    def "lombok non-tested methods"() {
        given:
        UUID uuid = UUID.randomUUID()
        LocalDateTime start = LocalDateTime.MIN
        Duration duration = Duration.ZERO
        String title = ""
        String notes = ""
        ExternalElement element = new ExternalElement(uuid, start, duration, title, notes)
        expect:
        element.getExternalUuid()
        element.setExternalUuid(UUID.randomUUID())
        !element.isScheduled()
        element.setScheduled(true)
        element.toString()
        element.equals(element)
        element.canEqual(element)
        element.hashCode()
    }

    def "constructor test"() {
        given:
        UUID uuid = UUID.randomUUID()
        LocalDateTime start = LocalDateTime.MIN
        Duration duration = Duration.ZERO
        String title = UUID.randomUUID().toString()
        String notes = UUID.randomUUID().toString()
        when:
        ExternalElement element = new ExternalElement(uuid, start, duration, title, notes)
        then:
        assert element.getExternalUuid() == uuid
        assert element.getStart() == start
        assert element.getDuration() == duration
        assert element.getTitle() == title
        assert element.getNotes() == notes
        assert !element.isScheduled()
    }
}
