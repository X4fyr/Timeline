package de.x4fyr.timeline.domain

import de.x4fyr.timeline.domain.stubs.ElementImpl
import spock.lang.Specification

/**
 * @author Benedikt Volkmer
 * Created on 10/30/16.
 */
class ElementSpec extends Specification {

    def "lombok non-tested methods"() {
        given:
        def String title = UUID.randomUUID().toString()
        def String notes = UUID.randomUUID().toString()
        def Element element = new ElementImpl(title, notes);
        expect:
        element.getTitle()
        element.getNotes()
        element.equals(element)
        element.canEqual(element)
        element.hashCode()
        element.toString()
        element.setTitle(title)
        element.setNotes(notes)
    }


}
