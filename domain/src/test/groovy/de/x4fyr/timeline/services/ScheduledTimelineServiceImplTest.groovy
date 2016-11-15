package de.x4fyr.timeline.services

import de.x4fyr.timeline.adapter.ScheduleAdapter
import de.x4fyr.timeline.domain.elements.ScheduledElement
import spock.lang.Specification

import java.time.*

/**
 * @author Benedikt Volkmer
 * Created on 11/14/16.
 */
class ScheduledTimelineServiceImplTest extends Specification {

    ScheduleAdapter scheduleAdapter;
    ScheduledTimelineServiceImpl service;

    def setup() {
        scheduleAdapter = Mock(ScheduleAdapter.class)
        service = new ScheduledTimelineServiceImpl(scheduleAdapter)
    }

    def "test getToday"() {
        given:
        def start = LocalDateTime.now().with(LocalTime.MIN);
        def end = LocalDateTime.now().with(LocalTime.MAX);
        def queryResult = generateQueryResults()
        when:
        def result = service.getToday()
        then:
        1 * scheduleAdapter.getByTimeSpan(start, end) >> queryResult
        assert result.containsAll(queryResult)
        assert result.size() == queryResult.size()
    }

    def "test getDate"() {
        given:
        def date = LocalDate.MIN
        def queryResults = generateQueryResults()
        when:
        def result = service.getDate(date)
        then:
        1 * scheduleAdapter.getByTimeSpan(date.atStartOfDay(), date.atTime(LocalTime.MAX)) >> queryResults
        assert result.containsAll(queryResults)
        assert result.size() == queryResults.size()
    }

    def "test getTimeSpan"() {
        given:
        int DAY_IN_MINUTES = 24 * 60
        def rand = new Random()
        def start = LocalDateTime.now()
        def end = LocalDateTime.now().plusMinutes(rand.nextInt(3 * DAY_IN_MINUTES))
        def queryResult = generateQueryResults()
        when:
        def result = service.getTimeSpan(start, end)
        then:
        1 * scheduleAdapter.getByTimeSpan(start, end) >> queryResult
        assert result.containsAll(queryResult)
        assert result.size() == queryResult.size()
    }


    def static private Collection<ScheduledElement> generateQueryResults() {
        Random random = new Random()
        def size = random.nextInt(101)
        List<ScheduledElement> result = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            LocalDateTime start = LocalDateTime.ofEpochSecond(random.nextInt(999999999), 0, ZoneOffset
                    .UTC)
            Duration duration = Duration.ofSeconds(random.nextLong())
            ScheduledElement element = new ScheduledElement(start, duration, "", "")
            result.add(element)
        }
        return result;
    }
}
