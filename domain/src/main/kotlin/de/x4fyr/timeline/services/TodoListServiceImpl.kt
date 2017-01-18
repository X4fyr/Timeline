package de.x4fyr.timeline.services

import de.x4fyr.timeline.adapter.TodoListAdapter
import de.x4fyr.timeline.domain.elements.TodoElement
import org.joda.time.LocalDate

class TodoListServiceImpl(val todoListAdapter: TodoListAdapter) : TodoListService {

    override fun getTodoList(): Set<TodoElement> = todoListAdapter.getAll()

    override fun getByDate(date: LocalDate): Set<TodoElement> = todoListAdapter.getByDate(date)
}