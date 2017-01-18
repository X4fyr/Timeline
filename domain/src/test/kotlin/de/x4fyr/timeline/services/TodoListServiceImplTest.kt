package de.x4fyr.timeline.services

import de.x4fyr.timeline.adapter.TodoListAdapter
import de.x4fyr.timeline.domain.elements.TodoElement
import de.x4fyr.util.nextDuration
import de.x4fyr.util.nextLocalDate
import de.x4fyr.util.nextString
import org.junit.Test

import org.mockito.BDDMockito.*
import org.assertj.core.api.Assertions.*
import org.junit.Ignore
import java.util.HashSet
import java.util.Random

/**
 * @author x4fyr
 * *         Created on 1/15/17.
 */
class TodoListServiceImplTest {

    companion object {
        val RANDOM = Random()
    }

    @Test
    fun getTodoList() {
        //given
        val todoAdapter = mock(TodoListAdapter::class.java)
        val service = TodoListServiceImpl(todoAdapter)
        val data = randomData();
        given(todoAdapter.getAll()).willReturn(data)
        //when
        val result = service.getTodoList()
        //then
        then(todoAdapter).should(only()).getAll()
        then(todoAdapter).shouldHaveNoMoreInteractions()
        assertThat(result).containsOnlyElementsOf(data)
    }

    @Test
    fun getByDate() {
        //given
        val todoAdapter = mock(TodoListAdapter::class.java)
        val service = TodoListServiceImpl(todoAdapter)
        val data = randomData();
        val date = RANDOM.nextLocalDate()
        given(todoAdapter.getByDate(date)).willReturn(data)
        //when
        val result = service.getByDate(date)
        //then
        then(todoAdapter).should(only()).getByDate(date)
        then(todoAdapter).shouldHaveNoMoreInteractions()
        assertThat(result).containsOnlyElementsOf(data)
    }

    fun randomData(): Set<TodoElement> {
        val result: MutableSet<TodoElement> = HashSet<TodoElement>()
        val ids: MutableList<Long> = mutableListOf()
        RANDOM.longs().boxed().limit(100).forEach { ids.add(it) }
        for (id in ids) {
            val title = RANDOM.nextString(20)
            val notes = RANDOM.nextString(100)
            val date = if (RANDOM.nextBoolean()) RANDOM.nextLocalDate() else null
            val duration =  if (RANDOM.nextBoolean()) RANDOM.nextDuration() else null
            val element = TodoElement(id = id, title = title, notes = notes, plannedDate = date,
                    plannedDuration = duration)
            result.add(element)
        }
        return result
    }

}