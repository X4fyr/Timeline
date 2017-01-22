package timeline

import timeline.adapter.ExternalScheduleAdapter
import timeline.adapter.ScheduleAdapter
import timeline.adapter.TodoListAdapter
import timeline.services.*

/**
 * Interface for a service provider
 */
abstract class ServiceProvider {
    val elementService: ElementService by lazy {
        ElementServiceImpl(
                scheduleAdapter = scheduleAdapter,
                externalScheduleAdapter = externalScheduleAdapter,
                todoListAdapter = todoListAdapter)
    }
    val scheduleService: ScheduleService by lazy { ScheduleServiceImpl(scheduleAdapter) }
    val todoListService: TodoListService by lazy { TodoListServiceImpl(todoListAdapter) }

    abstract protected val scheduleAdapter: ScheduleAdapter
    abstract protected val todoListAdapter: TodoListAdapter
    abstract protected val externalScheduleAdapter: ExternalScheduleAdapter

}