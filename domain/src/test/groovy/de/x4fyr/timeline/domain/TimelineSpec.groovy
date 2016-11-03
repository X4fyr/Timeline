package de.x4fyr.timeline.domain

import spock.lang.Specification

import java.time.LocalDateTime
import java.time.ZoneOffset

/**
 * @author Benedikt Volkmer
 * Created on 11/1/16.
 */
class TimelineSpec extends Specification {

    def "test empty constructor"() {
        when:
        Timeline timeline = new Timeline()
        then:
        timeline.size() == 0
    }

    def "test add, addFirst and addLast addIndexed"() { // All should have the same effect
        given:
        def Element preset1 = Stub(Element.class) { getStart() >> LocalDateTime.ofEpochSecond(a, 0, ZoneOffset.UTC) }
        def Element preset2 = Stub(Element.class) { getStart() >> LocalDateTime.ofEpochSecond(b, 0, ZoneOffset.UTC) }
        def Element addition = Stub(Element.class) { getStart() >> LocalDateTime.ofEpochSecond(c, 0, ZoneOffset.UTC) }
        def Timeline add = new Timeline(preset1, preset2)
        def Timeline addFirst = new Timeline(preset1, preset2)
        def Timeline addLast = new Timeline(preset1, preset2)
        def Timeline addIndexed = new Timeline(preset1, preset2)
        when:
        add.add(addition)
        addFirst.addFirst(addition)
        addLast.addLast(addition)
        addIndexed.add(3, addition);
        then:
        assert add.get(position) == addition
        assert addFirst.get(position) == addition
        assert addLast.get(position) == addition
        assert addIndexed.get(position) == addition
        where:
        a | b | c
        1 | 2 | 3
        3 | 1 | 2
        2 | 3 | 1

        position = c - 1
    }

    def "test addAll and collection constructor"() {
        given:
        def Timeline addAll = new Timeline();
        def Timeline addAllIndexed = new Timeline();
        def Timeline constructor;
        def Element elementA = Stub(Element.class) { getStart() >> LocalDateTime.ofEpochSecond(a, 0, ZoneOffset.UTC) }
        def Element elementB = Stub(Element.class) { getStart() >> LocalDateTime.ofEpochSecond(b, 0, ZoneOffset.UTC) }
        def Element elementC = Stub(Element.class) { getStart() >> LocalDateTime.ofEpochSecond(c, 0, ZoneOffset.UTC) }
        def List list = [elementA, elementB, elementC]
        when:
        addAll.addAll(list);
        addAllIndexed.addAll(2, list)
        constructor = new Timeline(list)
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

    def "test sort"() {
        given:
        def Element elementA = Stub(Element.class) {
            getStart() >> LocalDateTime.ofEpochSecond(a, 0, ZoneOffset.UTC)
            getTitle() >> b.toString()
        }
        def Element elementB = Stub(Element.class) {
            getStart() >> LocalDateTime.ofEpochSecond(b, 0, ZoneOffset.UTC)
            getTitle() >> c.toString()
        }
        def Element elementC = Stub(Element.class) {
            getStart() >> LocalDateTime.ofEpochSecond(c, 0, ZoneOffset.UTC)
            getTitle() >> a.toString()
        }
        Timeline timeline = new Timeline(elementA, elementB, elementC)
        def Comparator<Element> comparator = new Comparator<Element>() {
            @Override
            int compare(Element o1, Element o2) {
                o1.title.compareTo(o2.title)
            }
        }
        when:
        timeline.sort(comparator)
        then:
        assert timeline.get(indexA) == elementA
        assert timeline.get(indexB) == elementB
        assert timeline.get(indexC) == elementC
        where:
        a | b | c
        1 | 2 | 3
        3 | 1 | 2
        2 | 3 | 1
        indexA = a - 1
        indexB = b - 1
        indexC = c - 1
    }

}
