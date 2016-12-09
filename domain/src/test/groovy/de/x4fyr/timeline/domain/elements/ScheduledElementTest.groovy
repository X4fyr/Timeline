package de.x4fyr.timeline.domain.elements

import spock.lang.Specification

import java.time.Duration
import java.time.LocalDateTime

/**
 * @author x4fyr
 * Created on 12/8/16.
 */
class ScheduledElementTest extends Specification {

    static LocalDateTime START = LocalDateTime.MIN.plusDays(2)
    static LocalDateTime START2 = LocalDateTime.MIN.plusDays(3)
    static Duration DURATION = Duration.ZERO
    static Duration DURATION2 = Duration.ofDays(1)
    static String TITLE = "title"
    static String NOTES = "notes"
    static UUID EXT_UUID = UUID.randomUUID()

    def "test compareTo"() {
        given:
        def element = new ScheduledElement(START, DURATION, TITLE, NOTES, EXT_UUID)
        def greater = new ScheduledElement(START.plusDays(1), DURATION, TITLE, NOTES, EXT_UUID)
        def lesser = new ScheduledElement(START.minusDays(1), DURATION, TITLE, NOTES, EXT_UUID)
        def equal = new ScheduledElement(START, DURATION, TITLE, NOTES, EXT_UUID)
        expect:
        element < greater
        element > lesser
        element == equal
    }

    def "test getStart"() {
        given:
        def element = new ScheduledElement(START, DURATION, TITLE, NOTES, EXT_UUID)
        expect:
        element.getStart() == START
    }

    def "test setStart"() {
        given:
        def element = new ScheduledElement(START, DURATION, TITLE, NOTES, EXT_UUID)
        when:
        element.setStart(START2)
        then:
        element.start == START2
    }

    def "test getDuration"() {
        given:
        def element = new ScheduledElement(START, DURATION, TITLE, NOTES, EXT_UUID)
        expect:
        element.getDuration() == DURATION
    }

    def "test setDuration"() {
        given:
        def element = new ScheduledElement(START, DURATION, TITLE, NOTES, EXT_UUID)
        when:
        element.setDuration(DURATION2)
        then:
        element.duration == DURATION2
    }

    def "test getExternalUUID"() {
        given:
        def element = new ScheduledElement(START, DURATION, TITLE, NOTES, EXT_UUID)
        expect:
        element.getExternalUUID() == EXT_UUID
    }

    def "test setExternalUUID"() {
        given:
        def element = new ScheduledElement(START, DURATION, TITLE, NOTES, null)
        when:
        element.setExternalUUID(EXT_UUID)
        then:
        element.externalUUID == EXT_UUID
    }
}
