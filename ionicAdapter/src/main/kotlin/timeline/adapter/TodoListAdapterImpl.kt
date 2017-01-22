package timeline.adapter

import timeline.domain.elements.TodoElement
import utils.date.DateOnly

class TodoListAdapterImpl : TodoListAdapter {
    override fun saveToToDoList(element: TodoElement): TodoElement {
        throw UnsupportedOperationException("not implemented") //TODO
    }

    override fun deleteFromTodoList(element: TodoElement) {
        throw UnsupportedOperationException("not implemented") //TODO
    }

    override fun getAll(): Set<TodoElement> {
        throw UnsupportedOperationException("not implemented") //TODO
    }

    override fun getByDate(date: DateOnly): Set<TodoElement> {
        throw UnsupportedOperationException("not implemented") //TODO
    }
}