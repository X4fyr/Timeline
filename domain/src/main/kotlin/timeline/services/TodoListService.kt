package timeline.services

import timeline.domain.elements.TodoElement
import utils.date.DateOnly

/**
 * @author x4fyr
 * Created on 1/15/17.
 */
interface TodoListService {

    fun getTodoList(): Set<TodoElement>

    fun getByDate(date: DateOnly): Set<TodoElement>
}