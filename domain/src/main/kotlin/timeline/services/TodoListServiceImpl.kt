package timeline.services

import timeline.adapter.TodoListAdapter
import timeline.domain.elements.TodoElement
import utils.date.DateOnly

class TodoListServiceImpl(val todoListAdapter: TodoListAdapter) : TodoListService {

    override fun getTodoList(): Set<TodoElement> = todoListAdapter.getAll()

    override fun getByDate(date: DateOnly): Set<TodoElement> = todoListAdapter.getByDate(date)
}