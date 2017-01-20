package timeline.adapter

import timeline.domain.elements.ScheduledElement
import utils.date.DateTime

/**
 * @author x4fyr
 * Created on 1/19/17.
 */
class ScheduleAdapterImpl : ScheduleAdapter {

    /** Save the given ScheduleElement to the schedule persistence */
    override fun saveToSchedule(element: ScheduledElement): ScheduledElement {
        throw UnsupportedOperationException("not implemented") //TODO
    }

    /** Delete given ScheduleElement from schedule persistence */
    override fun deleteFromSchedule(element: ScheduledElement) {
        throw UnsupportedOperationException("not implemented") //TODO
    }

    /** get ScheduleElements of given time span */
    override fun getByTimeSpan(start: DateTime, end: DateTime): Set<ScheduledElement> {
        throw UnsupportedOperationException("not implemented") //TODO
    }

    override fun getNext(element: ScheduledElement, count: Long): Set<ScheduledElement> {
        throw UnsupportedOperationException("not implemented") //TODO
    }

    override fun getPrev(element: ScheduledElement, count: Long): Set<ScheduledElement> {
        throw UnsupportedOperationException("not implemented") //TODO
    }
}