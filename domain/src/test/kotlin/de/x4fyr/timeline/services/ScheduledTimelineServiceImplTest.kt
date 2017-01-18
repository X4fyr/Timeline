package de.x4fyr.timeline.services

import de.x4fyr.timeline.adapter.ScheduleAdapter
import de.x4fyr.timeline.domain.Timeline
import de.x4fyr.timeline.domain.elements.ScheduledElement
import de.x4fyr.util.*
import org.junit.Test

import org.mockito.BDDMockito.*
import org.assertj.core.api.Assertions.*
import org.joda.time.LocalDateTime
import org.joda.time.LocalTime
import org.junit.Ignore
import org.mockito.ArgumentMatchers
import java.util.HashSet
import java.util.Random

/**
 * @author x4fyr
 * *         Created on 1/16/17.
 */
class ScheduledTimelineServiceImplTest {

    companion object {
        val RANDOM = Random()
    }

    @Test
    fun expandForward() {
        //given
        val scheduleAdapter = mock(ScheduleAdapter::class.java)
        val service = ScheduledTimelineServiceImpl(scheduleAdapter)
        val (start, duration, end) = RANDOM.nextStartDurationEnd()
        val elem1 = ScheduledElement(id = 3, title = RANDOM.nextString(), notes = RANDOM.nextString(), start = start,
                duration = RANDOM.nextDuration())
        val elem2 = ScheduledElement(id = 4, title = RANDOM.nextString(), notes = RANDOM.nextString(), start = start.plus
        (duration/2), duration = RANDOM.nextDuration())
        val elem3 = ScheduledElement(id = 5, title = RANDOM.nextString(), notes = RANDOM.nextString(), start = end,
                duration = RANDOM.nextDuration())
        val timeline = Timeline<ScheduledElement>(elem1, elem2, elem3)
        val count: Long = 3
        val data = getRandomData(count)
        given(scheduleAdapter.getNext(elem3, count)).willReturn(data)
        //when
        val result = service.expandForward(timeline, count)
        //then
        then(scheduleAdapter).should().getNext(elem3, count)
        then(scheduleAdapter).shouldHaveNoMoreInteractions()
        val wantedTimeline = Timeline<ScheduledElement>(timeline)
        wantedTimeline.addAll(data)
        assertThat(result).containsOnlyElementsOf(wantedTimeline)
    }

    @Test
    fun expandBackward() {
        //given
        val scheduleAdapter = mock(ScheduleAdapter::class.java)
        val service = ScheduledTimelineServiceImpl(scheduleAdapter)
        val (start, duration, end) = RANDOM.nextStartDurationEnd()
        val elem1 = ScheduledElement(id = 3, title = RANDOM.nextString(), notes = RANDOM.nextString(), start = start,
                duration = RANDOM.nextDuration())
        val elem2 = ScheduledElement(id = 4, title = RANDOM.nextString(), notes = RANDOM.nextString(), start = start.plus
        (duration/2), duration = RANDOM.nextDuration())
        val elem3 = ScheduledElement(id = 5, title = RANDOM.nextString(), notes = RANDOM.nextString(), start = end,
                duration = RANDOM.nextDuration())
        val timeline = Timeline<ScheduledElement>(elem1, elem2, elem3)
        val count: Long = 3
        val data = getRandomData(count)
        given(scheduleAdapter.getPrev(elem1, count)).willReturn(data)
        //when
        val result = service.expandBackward(timeline, count)
        //then
        then(scheduleAdapter).should().getPrev(elem1, count)
        then(scheduleAdapter).shouldHaveNoMoreInteractions()
        val wantedTimeline = Timeline<ScheduledElement>(timeline)
        wantedTimeline.addAll(data)
        assertThat(result).containsOnlyElementsOf(wantedTimeline)
    }


    @Test
    fun getToday() {
        //given
        val scheduleAdapter = mock(ScheduleAdapter::class.java)
        val service = ScheduledTimelineServiceImpl(scheduleAdapter)
        val start = LocalDateTime.now().withTime(0, 0, 0, 0)
        val end = LocalDateTime.now().withTime(23, 59, 59, 999)
        val data = getRandomData()
        given(scheduleAdapter.getByTimeSpan(start, end)).willReturn(data)
        //when
        val result = service.getToday()
        //then
        then(scheduleAdapter).should(only()).getByTimeSpan(start, end)
        assertThat(result).containsOnlyElementsOf(data)
    }

    @Test
    fun getDate() {
        //given
        val scheduleAdapter = mock(ScheduleAdapter::class.java)
        val service = ScheduledTimelineServiceImpl(scheduleAdapter)
        val date = RANDOM.nextLocalDate()
        val data = getRandomData()
        given(scheduleAdapter.getByTimeSpan(date.toDateTimeAtStartOfDay().toLocalDateTime(),
                date.toLocalDateTime(LocalTime(23, 59, 59, 999)))).willReturn(data)
        //when
        val result = service.getDate(date)
        //then
        then(scheduleAdapter).should(only()).getByTimeSpan(date.toDateTimeAtStartOfDay().toLocalDateTime(),
                date.toLocalDateTime(LocalTime(23, 59, 59, 999)))
        then(scheduleAdapter).shouldHaveNoMoreInteractions()
        assertThat(result).containsOnlyElementsOf(data)
    }

    @Test
    fun getTimeSpan() {
        //given
        val scheduleAdapter = mock(ScheduleAdapter::class.java)
        val service = ScheduledTimelineServiceImpl(scheduleAdapter)
        val data = getRandomData()
        val (start, duration, end) = RANDOM.nextStartDurationEnd()
        given(scheduleAdapter.getByTimeSpan(start, end)).willReturn(data)
        //when
        val result = service.getTimeSpan(start, end)
        //then
        then(scheduleAdapter).should(only()).getByTimeSpan(start, end)
        then(scheduleAdapter).shouldHaveNoMoreInteractions()
        assertThat(result).containsOnlyElementsOf(data)
    }

    fun getRandomData(limit: Long = 100): Set<ScheduledElement> {
        val result: MutableSet<ScheduledElement> = HashSet()
        val ids: MutableList<Long> = mutableListOf()
        RANDOM.longs().boxed().limit(limit).forEach { ids.add(it) }
        for (id in ids) {
            val (start, duration, end) = RANDOM.nextStartDurationEnd()
            val title = RANDOM.nextString(20)
            val notes = RANDOM.nextString(100)
            val element = ScheduledElement(id, title, notes, start, duration, RANDOM
                    .nextLong())
            result.add(element)
        }
        return result
    }
}