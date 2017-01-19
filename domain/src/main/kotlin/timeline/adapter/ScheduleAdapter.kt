package timeline.adapter

import timeline.domain.elements.ScheduledElement
import util.jsToKotlin.date.DateTime


/**
 * @author Benedikt Volkmer
 *         Created on 11/14/16.
 */
interface ScheduleAdapter {

    /** Save the given ScheduleElement to the schedule persistence */
    fun saveToSchedule(element: ScheduledElement): ScheduledElement

    /** Delete given ScheduleElement from schedule persistence */
    fun deleteFromSchedule(element: ScheduledElement)

    /** get ScheduleElements of given time span */
    fun getByTimeSpan(start: DateTime, end: DateTime): Set<ScheduledElement>

    fun getNext(element: ScheduledElement, count: Long): Set<ScheduledElement>

    fun getPrev(element: ScheduledElement, count: Long): Set<ScheduledElement>
}
