package timeline

import timeline.adapter.*

class IonicServiceProvider() : ServiceProvider() {
    val id = Math.random()

    override val scheduleAdapter: ScheduleAdapter by lazy { ScheduleAdapterImpl() }
    override val todoListAdapter: TodoListAdapter by lazy { TodoListAdapterImpl() }
    override val externalScheduleAdapter: ExternalScheduleAdapter by lazy { ExternalScheduleAdapterImpl() }

}