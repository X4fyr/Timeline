package de.x4fyr.timeline.services

import de.x4fyr.timeline.domain.elements.TodoElement
import org.joda.time.LocalDate

/**
 * @author x4fyr
 * Created on 1/15/17.
 */
interface TodoListService {

    fun getTodoList(): Set<TodoElement>

    fun getByDate(date: LocalDate): Set<TodoElement>
}