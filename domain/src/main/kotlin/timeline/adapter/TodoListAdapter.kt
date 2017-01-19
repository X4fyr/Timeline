package timeline.adapter

import timeline.domain.elements.TodoElement
import util.jsToKotlin.date.DateOnly

/**
 * @author Benedikt Volkmer
 *         Created on 11/14/16.
 */
interface TodoListAdapter {

    fun saveToToDoList(element: TodoElement): TodoElement

    fun deleteFromTodoList(element: TodoElement)

    fun getAll(): Set<TodoElement>

    fun  getByDate(date: DateOnly): Set<TodoElement>
}
