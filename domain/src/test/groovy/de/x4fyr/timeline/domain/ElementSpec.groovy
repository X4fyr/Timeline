package de.x4fyr.timeline.domain

import spock.lang.Specification

import java.time.Duration
import java.time.LocalDateTime

/**
 * @author Benedikt Volkmer
 * Created on 10/30/16.
 */
class ElementSpec extends Specification {

    def "lombok non-tested methods"() {
        given:
        def LocalDateTime start = LocalDateTime.MIN
        def Duration duration = Duration.ZERO
        def String title = UUID.randomUUID().toString()
        def String notes = UUID.randomUUID().toString()
        def Element element = new Element(start, duration, title, notes);
        expect:
        element.getStart()
        element.getDuration()
        element.getTitle()
        element.getNotes()
        element.equals(element)
        element.canEqual(element)
        element.hashCode()
        element.toString()
        element.setStart(start)
        element.setDuration(duration)
        element.setTitle(title)
        element.setNotes(notes)
    }
}
