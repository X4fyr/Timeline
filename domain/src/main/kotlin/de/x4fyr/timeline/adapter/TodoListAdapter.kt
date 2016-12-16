package de.x4fyr.timeline.adapter

import de.x4fyr.timeline.domain.elements.TodoElement

/**
 * @author Benedikt Volkmer
 *         Created on 11/14/16.
 */
interface TodoListAdapter {

    fun saveToToDoList(element: TodoElement): TodoElement

    fun deleteFromTodoList(element: TodoElement)
}
