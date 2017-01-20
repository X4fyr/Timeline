package timeline.domain.elements

import utils.date.DateTime
import utils.date.Duration


/**
 * Element scheduled for a specific date and time.
 * @author Benedikt Volkmer
 *         Created on 11/4/16.
 */
data class ScheduledElement(val id: Long? = null,
                            var title: String,
                            var notes: String,
                            var start: DateTime,
                            var duration: Duration,
                            val externalId: Long? = null)
    : Comparable<ScheduledElement> {

    /** Compares the start DateTime */
    override fun compareTo(other: ScheduledElement) = start.compareTo(other.start)
}
