package de.x4fyr.timeline.domain.elements

import spock.lang.Specification

import org.joda.time.Duration
import org.joda.time.LocalDateTime

/**
 * @author x4fyr
 * Created on 12/8/16.
 */
class ScheduledElementTest extends Specification {

    private static final Random random = new Random()
    static LocalDateTime START = new LocalDateTime(0).plusDays(2)
    static Duration DURATION = Duration.ZERO
    static String TITLE = "title"
    static String NOTES = "notes"
    static Long EXT_UUID = random.nextLong()

    def "test compareTo"() {
        given:
        def element = new ScheduledElement(null, TITLE, NOTES, START, DURATION, EXT_UUID)
        def greater = new ScheduledElement(null, TITLE, NOTES, START.plusDays(1), DURATION, EXT_UUID)
        def lesser = new ScheduledElement(null, TITLE, NOTES, START.minusDays(1), DURATION, EXT_UUID)
        def equal = new ScheduledElement(null, TITLE, NOTES, START, DURATION, EXT_UUID)
        expect:
        assert element < greater
        assert element > lesser
        assert element == equal
    }
}
