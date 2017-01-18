package de.x4fyr.timeline.adapter

import de.x4fyr.timeline.domain.elements.TodoElement
import org.joda.time.LocalDate

/**
 * @author Benedikt Volkmer
 *         Created on 11/14/16.
 */
interface TodoListAdapter {

    fun saveToToDoList(element: TodoElement): TodoElement

    fun deleteFromTodoList(element: TodoElement)

    fun getAll(): Set<TodoElement>

    fun  getByDate(date: LocalDate): Set<TodoElement>
}
