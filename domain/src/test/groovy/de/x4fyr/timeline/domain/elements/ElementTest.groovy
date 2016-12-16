package de.x4fyr.timeline.domain.elements

import spock.lang.Specification

/**
 * @author x4fyr
 * Created on 12/8/16.
 */
class ElementTest extends Specification {
    static String TITLE = "title"
    static String TITLE2 = "title2"
    static String NOTES = "notes"
    static String NOTES2 = "notes2"

    def "test getTitle"() {
        given:
        def element = new ElementStub(TITLE, NOTES)
        expect:
        element.getTitle() == TITLE
    }

    def "test setTitle"() {
        given:
        def element = new ElementStub(TITLE, NOTES)
        when:
        element.setTitle(TITLE2)
        then:
        element.title == TITLE2
    }

    def "test getNotes"() {
        given:
        def element = new ElementStub(TITLE, NOTES)
        expect:
        element.getNotes() == NOTES
    }

    def "test setNotes"() {
        given:
        def element = new ElementStub(TITLE, NOTES)
        when:
        element.setNotes(NOTES2)
        then:
        element.notes == NOTES2
    }

    class ElementStub extends Element {
        ElementStub(String title, String notes) {
            super(title, notes)
        }
    }
}
